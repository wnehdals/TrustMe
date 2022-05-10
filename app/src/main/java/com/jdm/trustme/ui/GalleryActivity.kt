package com.jdm.trustme.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.jdm.trustme.R
import com.jdm.trustme.base.BaseActivity
import com.jdm.trustme.const.PICTURE_URI
import com.jdm.trustme.databinding.ActivityGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryActivity : BaseActivity<ActivityGalleryBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_gallery

    override fun subscribe() {
    }

    override fun initEvent() {
    }

    override fun initView() {
        intent.getParcelableExtra<Uri>(PICTURE_URI)?.let {
            Glide.with(this).load(it).into(binding.galleryImage)
        }
    }
}