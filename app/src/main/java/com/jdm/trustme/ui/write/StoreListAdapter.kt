package com.jdm.trustme.ui.write

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jdm.trustme.databinding.ItemStoreBinding
import com.jdm.trustme.model.entity.Store

class StoreListAdapter: ListAdapter<Store, StoreListAdapter.ViewHolder>(storeDifUtil) {
    var onClick: (Store) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store = getItem(position)
        if(store != null) {
            holder.bind(store)
            holder.binding.itemStoreName.setOnClickListener { onClick(store) }
        }

    }

    class ViewHolder(val binding: ItemStoreBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Store) {
            binding.itemStoreName.text = item.name
        }
    }
    companion object {
        val storeDifUtil = object : DiffUtil.ItemCallback<Store>() {
            override fun areItemsTheSame(oldItem: Store, newItem: Store): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Store, newItem: Store): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}