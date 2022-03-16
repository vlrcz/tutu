package com.vlad.tutu.feature.repositories.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vlad.tutu.R
import com.vlad.tutu.databinding.FragmentRepositoriesDetailBinding
import com.vlad.tutu.feature.repositories.list.Repository

class RepositoriesDetailFragment : Fragment(R.layout.fragment_repositories_detail) {

    companion object {
        private const val KEY_REPO = "repo"
        fun newInstance(repository: Repository): RepositoriesDetailFragment {
            return RepositoriesDetailFragment().apply {
                arguments = bundleOf(KEY_REPO to repository)
            }
        }
    }

    private val binding: FragmentRepositoriesDetailBinding by viewBinding(FragmentRepositoriesDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = requireArguments().getParcelable(KEY_REPO) as? Repository
        if (repository != null) {
            binding.detailId.text = repository.id.toString()
            binding.detailName.text = repository.name
            binding.detailFullName.text = repository.fullName
        }
    }
}