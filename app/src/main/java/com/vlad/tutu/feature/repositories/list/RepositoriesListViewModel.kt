package com.vlad.tutu.feature.repositories.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.tutu.isNetworkError
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class RepositoriesListViewModel @Inject constructor(
    private val repositoriesRepository: RepositoriesRepository
) : ViewModel() {

    private var state = RepositoriesListState(
        loading = false,
        itemsList = emptyList(),
        isFirstLoad = false
    )

    private val repositoriesListStateFlow = MutableStateFlow(state)
    val repositoriesListState = repositoriesListStateFlow.asStateFlow()

    fun firstLoad() {
        if (!state.isFirstLoad) {
            state = state.copy(loading = true, isFirstLoad = true)
            repositoriesListStateFlow.value = state
            fetchRepositories()
        }
    }

    fun refresh() {
        state = state.copy(itemsList = emptyList(), loading = true)
        repositoriesListStateFlow.value = state
        fetchRepositories()
    }

    private fun fetchRepositories() {
        viewModelScope.launch {
            flow { emit(repositoriesRepository.fetchPublicRepositories()) }
                .catch {
                    if (it.isNetworkError()) {
                        emit(emptyList())
                    } else {
                        Timber.d("fetch repositories from github error ${it.localizedMessage}")
                    }
                }
                .onEach { repositoriesRepository.insertListOfRepositoriesToDb(it) }
                .catch { Timber.d("insert repositories to db error ${it.localizedMessage}") }
                .map { repositoriesRepository.fetchRepositoriesFromDb() }
                .catch { Timber.d("fetch repositories from db error ${it.localizedMessage}") }
                .flowOn(Dispatchers.IO)
                .collect {
                    state = state.copy(loading = false, itemsList = it)
                    repositoriesListStateFlow.value = state
                }
        }
    }
}