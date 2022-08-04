package com.example.takewith.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.takewith.model.TakeableItem
import com.example.takewith.databinding.ListItemTakeableItemBinding

class TakeableItemsListAdapter(
    private val clickListener: (TakeableItem) -> Unit
) : ListAdapter<TakeableItem, TakeableItemsListAdapter.TakeableViewHodler>(DiffCallback) {

    class TakeableViewHodler(
        private var binding: ListItemTakeableItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(takeableItem: TakeableItem) {
            binding.takeableItem = takeableItem
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<TakeableItem>() {
        override fun areItemsTheSame(oldItem: TakeableItem, newItem: TakeableItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TakeableItem, newItem: TakeableItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TakeableViewHodler {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TakeableViewHodler(
            ListItemTakeableItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TakeableViewHodler, position: Int) {
        val takeableItem = getItem(position)
        holder.itemView.setOnClickListener{
            clickListener(takeableItem)
        }
        holder.bind(takeableItem)
    }
}
