package com.vlad.tutu.repository_list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vlad.tutu.R
import com.vlad.tutu.app.appComponent
import com.vlad.tutu.databinding.FragmentRepositoryListBinding
import com.vlad.tutu.di.ViewModelFactory
import com.vlad.tutu.navigation.navigator
import com.vlad.tutu.toast
import javax.inject.Inject
import javax.inject.Provider

class RepoListFragment : Fragment(R.layout.fragment_repository_list) {

    @Inject
    lateinit var viewModelProvider: Provider<RepoListViewModel>
    private val binding: FragmentRepositoryListBinding by viewBinding(FragmentRepositoryListBinding::bind)
    private val viewModel: RepoListViewModel by viewModels { ViewModelFactory { viewModelProvider.get() } }
    private var repositoryAdapter: RepositoryAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindViewModel()
        backPress()
    }

    private fun bindViewModel() {
        viewModel.getRepositories()
        viewModel.repoList.observe(viewLifecycleOwner) { repositoryAdapter?.submitList(it) }
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

    private fun initList() = with(binding.repositoriesList) {
        repositoryAdapter = RepositoryAdapter { repo ->
            navigator().showDetailFragment(repo)
        }
        adapter = repositoryAdapter
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
    }


}