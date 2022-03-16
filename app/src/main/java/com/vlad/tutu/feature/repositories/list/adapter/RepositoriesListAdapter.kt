package com.vlad.tutu.feature.repositories.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vlad.tutu.databinding.ItemRepositoryBinding
import com.vlad.tutu.feature.repositories.list.Repository
import com.vlad.tutu.feature.repositories.list.adapter.RepositoriesListAdapter.RepositoryViewHolder

class RepositoriesListAdapter(
    private val onItemClicked: (repo: Repository) -> Unit
) : ListAdapter<Repository, RepositoryViewHolder>(RepositoryDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding =
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RepositoryViewHolder(
        private val binding: ItemRepositoryBinding,
        private val onItemClicked: (repo: Repository) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val item = (bindingAdapter as? RepositoriesListAdapter)?.getItem(bindingAdapterPosition)
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

    class RepositoryDiffUtilCallback : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }
    }
}