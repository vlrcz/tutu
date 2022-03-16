package com.vlad.tutu.feature.repositories.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.vlad.tutu.R
import com.vlad.tutu.databinding.ItemRepositoryBinding
import com.vlad.tutu.feature.repositories.list.Repository

class RepositoriesListAdapter(
    private val onItemClicked: (repo: Repository) -> Unit
) : ListAdapter<Repository, RepositoryViewHolder>(RepositoryDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
        return RepositoryViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}