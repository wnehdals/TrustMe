package com.jdm.trustme.ui.write.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jdm.trustme.databinding.ItemSelectedGalleryBinding
import com.jdm.trustme.model.response.Gallery
import java.util.*

class SelectedImageAdapter(private val context: Context, private val onClickClose: (Gallery, Int) -> Unit = { selectedItem, position -> }): RecyclerView.Adapter<SelectedImageAdapter.ViewHolder>() {
    val selectedList = LinkedList<Gallery>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSelectedGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gallery = selectedList[position]
        if (gallery != null) {
            holder.bind(gallery, position)
            holder.binding.itemSelectedGalleryClose.setOnClickListener {
                onClickClose(gallery, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return selectedList.size
    }
    fun addData(data: LinkedList<Gallery>) {
        selectedList.addAll(data)
        notifyDataSetChanged()
    }
    fun removeDataAt(position: Int) {
        selectedList.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemSelectedGalleryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Gallery, position: Int) {
            if (item.bitmap == null) {
                if (!item.uri.toString().isNullOrEmpty()) {
                    Glide.with(context).load(item.uri).into(binding.itemSelectedGalleryImg)
                }
            } else {
                binding.itemSelectedGalleryImg.setImageBitmap(item.bitmap)
            }

        }
    }
    companion object {
        val selectedImageAdapterDiffUtil = object : DiffUtil.ItemCallback<Gallery>() {
            override fun areItemsTheSame(oldItem: Gallery, newItem: Gallery): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Gallery, newItem: Gallery): Boolean {
                return oldItem == newItem
            }
        }
    }
}