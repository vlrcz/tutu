package com.vlad.tutu.feature.repositories.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vlad.tutu.databinding.ItemRepositoryBinding
import com.vlad.tutu.feature.repositories.list.Repository

class RepositoryViewHolder(
    view: View,
    private val onItemClicked: (repo: Repository) -> Unit
) : ViewHolder(view) {

    private val binding: ItemRepositoryBinding = ItemRepositoryBinding.bind(view)

    init {
        itemView.setOnClickListener {
            val item = (bindingAdapter as? RepositoriesListAdapter)?.currentList?.get(bindingAdapterPosition)
                ?: return@setOnClickListener
            onItemClicked.invoke(item)
        }
    }

    fun bind(repository: Repository) {
        binding.idTextView.text = repository.id.toString()
        binding.nameTextView.text = repository.name
        binding.fullnameTextView.text = repository.fullName
    }
}