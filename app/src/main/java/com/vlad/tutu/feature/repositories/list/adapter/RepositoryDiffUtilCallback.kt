package com.vlad.tutu.feature.repositories.list.adapter

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.vlad.tutu.feature.repositories.list.Repository

class RepositoryDiffUtilCallback : ItemCallback<Repository>() {
    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }
}