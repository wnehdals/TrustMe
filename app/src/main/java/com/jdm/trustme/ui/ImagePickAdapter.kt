package com.jdm.trustme.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jdm.trustme.R
import com.jdm.trustme.databinding.ItemGalleryMediaBinding
import com.jdm.trustme.model.entity.Gallery
import com.jdm.trustme.model.media.Media
import java.util.*

class ImagePickAdapter(private val context: Context, private val onClickImageView: (Gallery) -> Unit) :
    RecyclerView.Adapter<ImagePickAdapter.ViewHolder>() {
    private val mediaUris: MutableList<Gallery> = mutableListOf()
    private val selectedList = LinkedList<Gallery>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGalleryMediaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mediaUris[position]
        if (item != null) {
            holder.bind(item, position)
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

    inner class ViewHolder(val binding: ItemGalleryMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Gallery, position: Int) {
            Glide.with(context).load(item.uri).into(binding.itemGalleryImg)
            if (item.selectedNum > 0) {
                toggleItemGalleryNumber(true, item)
            } else {
                toggleItemGalleryNumber(false, item)
            }
            binding.itemGalleryNumber.setOnClickListener {
                if (item.selectedNum > 0) {
                    deleteSelectedItem(mediaUris[position], position)
                    toggleItemGalleryNumber(false, item)
                } else {
                    addSelectedItem(mediaUris[position], position)
                    toggleItemGalleryNumber(true, item)
                }
            }
            binding.itemGalleryImg.setOnClickListener {
                onClickImageView(item)
            }
        }

        fun toggleItemGalleryNumber(isSelected: Boolean, item: Gallery) {
            if (isSelected) {
                binding.itemGalleryNumber.text = "${item.selectedNum}"
                val resId = ContextCompat.getDrawable(context, R.drawable.bg_multi_image_selected)
                binding.itemGalleryNumber.background = resId
            } else {
                binding.itemGalleryNumber.text = ""
                val resId = ContextCompat.getDrawable(context, R.drawable.bg_multi_image_unselected)
                binding.itemGalleryNumber.background = resId
            }

        }

        fun addSelectedItem(item: Gallery, position: Int) {
            selectedList.add(item)
            mediaUris[position].selectedNum = selectedList.size
        }

        fun deleteSelectedItem(item: Gallery, position: Int) {
            var idx = 0
            for (i in 0 until selectedList.size) {
                if (item.id == selectedList[i].id) {
                    idx = i
                    break
                }
            }
            for (i in idx + 1 until selectedList.size) {
                for (j in 0 until mediaUris.size) {
                    if (selectedList[i].id == mediaUris[j].id) {

                        mediaUris[j].selectedNum = i
                        notifyItemChanged(j)
                        break
                    }

                }
            }
            mediaUris[position].selectedNum = 0
            selectedList.removeAt(idx)
            notifyItemChanged(position)


        }
    }

}