package com.jdm.trustme.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jdm.trustme.databinding.ItemGalleryMediaBinding
import com.jdm.trustme.model.entity.Gallery
import com.jdm.trustme.model.media.Media

class ImagePickAdapter(private val context: Context): RecyclerView.Adapter<ImagePickAdapter.ViewHolder>() {
    private val mediaUris: MutableList<Gallery> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGalleryMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mediaUris[position]
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return mediaUris.size
    }
    fun addData(list: MutableList<Gallery>) {
        mediaUris.clear()
        mediaUris.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemGalleryMediaBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Gallery) {
            Glide.with(context).load(item.uri).into(binding.ivImage)
        }
    }
}