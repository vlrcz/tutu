package com.vlad.tutu.feature.repositories.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vlad.tutu.R
import com.vlad.tutu.R.drawable
import com.vlad.tutu.app.appComponent
import com.vlad.tutu.core.navigation.NavigationConstants.DETAIL
import com.vlad.tutu.core.navigation.navigate
import com.vlad.tutu.core.navigation.screen.FragmentScreen
import com.vlad.tutu.databinding.FragmentRepositoriesListBinding
import com.vlad.tutu.di.ViewModelFactory
import com.vlad.tutu.feature.repositories.detail.RepositoriesDetailFragment
import com.vlad.tutu.feature.repositories.list.adapter.RepositoriesListAdapter
import com.vlad.tutu.feature.repositories.list.di.DaggerRepositoriesListComponent
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.coroutines.flow.collect

class RepositoriesListFragment : Fragment(R.layout.fragment_repositories_list) {

    @Inject
    lateinit var viewModelProvider: Provider<RepositoriesListViewModel>
    private val viewModel: RepositoriesListViewModel by viewModels { ViewModelFactory { viewModelProvider.get() } }
    private val binding: FragmentRepositoriesListBinding by viewBinding(FragmentRepositoriesListBinding::bind)
    private var repositoriesListAdapter: RepositoriesListAdapter =
        RepositoriesListAdapter {
            navigate(FragmentScreen(RepositoriesDetailFragment.newInstance(it), DETAIL))
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerRepositoriesListComponent.factory().create(context.appComponent).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindViewModel()
        bindSwipeToRefresh()
        addBackPressCallback()
        viewModel.firstLoad()
    }

    private fun bindViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.repositoriesListState.collect {
                binding.progressBar.isVisible = it.loading
                repositoriesListAdapter.submitList(it.itemsList)
                binding.emptyListTextView.isVisible = it.itemsList.isEmpty()
            }
        }
    }

    private fun bindSwipeToRefresh() {
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.refresh()
            binding.swipeToRefresh.isRefreshing = false
        }
    }

    private fun initList() {
        val dividerItemDecoration = createDividerItemDecoration()
        with(binding.repositoriesList) {
            adapter = repositoriesListAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun createDividerItemDecoration(): DividerItemDecoration {
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        val drawable = ResourcesCompat.getDrawable(resources, drawable.divider, null)
        if (drawable != null) {
            dividerItemDecoration.setDrawable(drawable)
        }
        return dividerItemDecoration
    }

    private fun addBackPressCallback() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }
}