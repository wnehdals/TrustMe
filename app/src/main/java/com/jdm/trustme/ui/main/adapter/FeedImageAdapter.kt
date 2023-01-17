package com.jdm.trustme.ui.main.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jdm.trustme.databinding.ItemFeedImageBinding

class FeedImageAdapter(private val context: Context) :
    RecyclerView.Adapter<FeedImageAdapter.ViewHolder>() {
    val uriList = mutableListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFeedImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = uriList[position]
        if (image != null) {
            holder.bind(image, position)
        }
    }

    override fun getItemCount(): Int {
        return uriList.size
    }

    fun addData(data: MutableList<Uri>) {
        uriList.clear()
        uriList.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemFeedImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Uri, position: Int) {
            if (!item.toString().isNullOrEmpty()) {
                Glide.with(context).load(item).into(binding.itemSelectedGalleryImg)

            }
        }

    }
}