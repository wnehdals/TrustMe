package com.jdm.trustme.ui.write.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jdm.trustme.R
import com.jdm.trustme.databinding.ItemGalleryMediaBinding
import com.jdm.data.model.entity.response.Gallery
import com.jdm.trustme.ui.write.WriteViewModel

class ImagePickAdapter(
    private val context: Context,
    private val viewModel: WriteViewModel,
    private val onClickImageView: (Gallery, Int) -> Unit = { selectedItem, position -> }
) :
    RecyclerView.Adapter<ImagePickAdapter.ViewHolder>() {
    private val mediaUris: MutableList<Gallery> = mutableListOf()
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

    fun addData(gallery: Gallery) {
        viewModel.selectedGallery.add(gallery)
        mediaUris.add(gallery)
        mediaUris[mediaUris.size - 1].selectedNum = viewModel.selectedGallery.size
        notifyItemChanged(mediaUris.size - 1)
        mediaUris.forEach {
            Log.e("jdm_tag", "${it.selectedNum} / ${it.id}")
        }
    }

    fun getList(): MutableList<Gallery> {
        return mediaUris
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
                }
            }
            binding.itemGalleryImg.setOnClickListener {
                if (item.selectedNum > 0) {
                    onClickImageView(item, getSelectedPosition(item))
                } else {
                    addSelectedItem(mediaUris[position], position)
                    toggleItemGalleryNumber(true, item)
                }
            }
        }
        fun getSelectedPosition(item: Gallery): Int {
            var position = 0
            for(i in 0 until viewModel.selectedGallery.size) {
                if (viewModel.selectedGallery[i].id == item.id) {
                    position = i
                    break
                }
            }
            return position
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
            viewModel.selectedGallery.add(item)
            mediaUris[position].selectedNum = viewModel.selectedGallery.size
            toggleItemGalleryNumber(true, item)
        }

        fun deleteSelectedItem(item: Gallery, position: Int) {
            var idx = 0
            for (i in 0 until viewModel.selectedGallery.size) {
                if (item.id == viewModel.selectedGallery[i].id) {
                    idx = i
                    break
                }
            }
            for (i in idx + 1 until viewModel.selectedGallery.size) {
                for (j in 0 until mediaUris.size) {
                    if (viewModel.selectedGallery[i].id == mediaUris[j].id) {

                        mediaUris[j].selectedNum = i
                        notifyItemChanged(j)
                        break
                    }

                }
            }
            mediaUris[position].selectedNum = 0
            viewModel.selectedGallery.removeAt(idx)
            notifyItemChanged(position)


        }
    }

}