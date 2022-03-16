package com.vlad.tutu.feature.repositories.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vlad.tutu.R
import com.vlad.tutu.app.appComponent
import com.vlad.tutu.core.navigation.NavigationConstants.DETAIL
import com.vlad.tutu.core.navigation.navigate
import com.vlad.tutu.core.navigation.screen.FragmentScreen
import com.vlad.tutu.databinding.FragmentRepositoriesListBinding
import com.vlad.tutu.di.ViewModelFactory
import com.vlad.tutu.feature.repositories.detail.RepositoriesDetailFragment
import com.vlad.tutu.feature.repositories.list.adapter.RepositoriesListAdapter
import com.vlad.tutu.feature.repositories.list.di.DaggerRepositoriesListComponent
import com.vlad.tutu.toast
import javax.inject.Inject
import javax.inject.Provider

class RepositoriesListFragment : Fragment(R.layout.fragment_repositories_list) {

    @Inject
    lateinit var viewModelProvider: Provider<RepositoriesListViewModel>
    private val binding: FragmentRepositoriesListBinding by viewBinding(
        FragmentRepositoriesListBinding::bind
    )
    private val viewModel: RepositoriesListViewModel by viewModels { ViewModelFactory { viewModelProvider.get() } }
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
        backPress()
    }

    private fun bindViewModel() {
        viewModel.getRepositories()
        viewModel.repoList.observe(viewLifecycleOwner) { repositoriesListAdapter.submitList(it) }
        viewModel.toast.observe(viewLifecycleOwner) {
            toast(it)
        }
        binding.pullToRefresh.setOnRefreshListener {
            viewModel.getRepositories()
            binding.pullToRefresh.isRefreshing = false
        }
    }

    private fun backPress() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    private fun initList() {
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
        if (drawable != null) {
            dividerItemDecoration.setDrawable(drawable)
        }
        with(binding.repositoriesList) {
            adapter = repositoriesListAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(dividerItemDecoration)
        }
    }
}