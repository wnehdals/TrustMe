package com.jdm.trustme.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jdm.trustme.databinding.ItemFeedBinding
import com.jdm.trustme.model.entity.Food
import com.jdm.trustme.model.entity.Store
import com.jdm.trustme.model.response.Feed
import com.jdm.trustme.model.response.Gallery
import com.jdm.trustme.util.ColorUtil
import com.jdm.trustme.util.GalleryUtil
import java.util.*

class FeedAdapter(private val context: Context, private val onClickFood: (Feed, Int) -> Unit = { selectedItem, position -> }): RecyclerView.Adapter<FeedAdapter.ViewHolder>() {
    val feedList = mutableListOf<Feed>()
    val feedAdapter = FeedImageAdapter(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = feedList[position]
        if (item != null) {
            holder.bind(item, position)
        }
    }

    override fun getItemCount(): Int {
        return feedList.size
    }
    fun addData(data: List<Feed>) {
        feedList.clear()
        feedList.addAll(data)
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding: ItemFeedBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Feed, position: Int) {
            with(binding) {
                feedIv.setImageDrawable(ContextCompat.getDrawable(context, ColorUtil.getCircleDrawable(item.store.img)))
                feedContentTv.text = item.food.content
                feedStoreNameTv.text = item.store.name
                feedFoodNameTv.text = item.food.name
                feedImgRv.adapter = feedAdapter
                val uriList = GalleryUtil.getUriList(context, item.food.imgId)
                if (item.food.imgId.size > 1) {
                    feedImgRv.visibility = View.VISIBLE
                    feedImgIv.visibility = View.GONE
                    feedAdapter.addData(uriList)
                } else {
                    feedImgRv.visibility = View.GONE
                    feedImgIv.visibility = View.VISIBLE
                    Glide.with(context).load(uriList[0]).into(feedImgIv)
                }

            }
        }
    }
}