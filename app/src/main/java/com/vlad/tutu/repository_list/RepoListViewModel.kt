package com.vlad.tutu.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.tutu.R
import com.vlad.tutu.isNetworkError
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RepoListViewModel @Inject constructor(
    private val repoRepository: RepoRepository
) :
    ViewModel() {

    private val repoListLiveData = MutableLiveData<List<Repository>>(emptyList())
    private val toastLiveData = MutableLiveData<Int>()

    val repoList: LiveData<List<Repository>>
        get() = repoListLiveData

    val toast: LiveData<Int>
        get() = toastLiveData

    fun getRepositories() {
        viewModelScope.launch {
            flow {
                emit(repoRepository.getUserRepositories())
            }
                .onEach {
                    repoRepository.insertListOfReposToDb(it)
                }
                .catch {
                    if (it.isNetworkError()) {
                        toastLiveData.postValue(R.string.list_from_db)
                    } else {
                        toastLiveData.postValue(R.string.loading_error)
                    }
                }
                .map {
                    repoRepository.getReposFromDb()
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    repoListLiveData.postValue(it)
                }
        }
    }
}