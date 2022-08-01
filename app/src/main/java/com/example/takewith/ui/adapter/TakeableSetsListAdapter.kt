package com.example.takewith.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.takewith.databinding.ListItemTakeableSetBinding
import com.example.takewith.model.TakeableSet


class TakeableSetsListAdapter(
    private val clickListener: (TakeableSet) -> Unit
): ListAdapter<TakeableSet, TakeableSetsListAdapter.TakeableSetsViewHolder>(DiffCallback) {
    class TakeableSetsViewHolder(
        private var binding: ListItemTakeableSetBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(takeableSet: TakeableSet) {
            binding.takeableSetItem = takeableSet
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<TakeableSet>() {
        override fun areItemsTheSame(oldItem: TakeableSet, newItem: TakeableSet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TakeableSet, newItem: TakeableSet): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TakeableSetsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TakeableSetsViewHolder(
            ListItemTakeableSetBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TakeableSetsViewHolder, position: Int) {
        val takeableSet = getItem(position)
        holder.itemView.setOnClickListener{
            clickListener(takeableSet)
        }
        holder.bind(takeableSet)
    }
}