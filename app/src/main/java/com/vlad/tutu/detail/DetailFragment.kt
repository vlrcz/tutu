package com.vlad.tutu.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vlad.tutu.R
import com.vlad.tutu.databinding.FragmentDetailBinding
import com.vlad.tutu.repository_list.Repository

class DetailFragment : Fragment(R.layout.fragment_detail) {

    companion object {
        private const val KEY_REPO = "repo"
        fun newInstance(repo: Repository?): DetailFragment {
            return DetailFragment().apply {
                arguments = bundleOf(KEY_REPO to repo)
            }
        }
    }

    private val binding: FragmentDetailBinding by viewBinding(FragmentDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repo = requireArguments().getParcelable(KEY_REPO) as? Repository
        if (repo != null) {
            binding.detailId.text = repo.id.toString()
            binding.detailName.text = repo.name
            binding.detailFullName.text = repo.fullName
        }
    }
}