package com.jdm.trustme.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jdm.trustme.databinding.ItemStoreCircleBinding
import com.jdm.trustme.model.entity.Store
import com.jdm.trustme.util.ColorUtil

class SelectedStoreAdapter(private val context: Context, private val onClickStore: (Store, Int) -> Unit = { selectedItem, position -> }): RecyclerView.Adapter<SelectedStoreAdapter.ViewHolder>() {
    val storeList = mutableListOf<Store>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemStoreCircleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(ItemStoreCircleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = storeList[position]
        if (item != null) {
            holder.bind(item, position)
        }
    }

    override fun getItemCount(): Int {
        return storeList.size
    }
    fun addData(data: List<Store>) {
        storeList.clear()
        storeList.addAll(data)
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding: ItemStoreCircleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Store, position: Int) {
            binding.storeCircleIv.setImageDrawable(ContextCompat.getDrawable(context, ColorUtil.getCircleDrawable(item.img)))
            binding.storeCircleTv.text = item.name
        }
    }
}