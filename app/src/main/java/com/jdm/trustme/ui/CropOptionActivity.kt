package com.jdm.trustme.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.jdm.trustme.R
import com.jdm.trustme.base.BaseActivity
import com.jdm.trustme.const.BITMAP
import com.jdm.trustme.const.GALLERY
import com.jdm.trustme.const.PICTURE_URI
import com.jdm.trustme.databinding.ActivityCropOptionBinding
import com.jdm.trustme.util.RotateTransformation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CropOptionActivity : BaseActivity<ActivityCropOptionBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_crop_option
    var imageUri: Uri? = null
    val rotateTransformation = RotateTransformation(0f)
    var tempBitmap: Bitmap? = null
    val viewModel: CropOptionViewModel by viewModels()
    lateinit var editImageFragment: EditImageFragment
    override fun subscribe() {

    }

    override fun initEvent() {
    }

    override fun initView() {
        intent.getParcelableExtra<Uri>(PICTURE_URI)?.let {
            uriToBitmap(it)

        }
    }
    fun uriToBitmap(uri: Uri, options: RequestOptions? = null) {
        if (options == null) {
            Glide.with(this).asBitmap().load(uri).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    viewModel.setBitmap(resource)
                    addEditImageFragment()
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        } else {
            Glide.with(this).asBitmap().apply(options).load(uri).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    viewModel.setBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        }
    }
    fun addEditImageFragment() {
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, EditImageFragment.newInstance(), EditImageFragment.TAG).commitAllowingStateLoss()
    }
    fun addCropRatioFragment() {
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, CropRatioFragment.newInstance(), CropRatioFragment().tag).commitAllowingStateLoss()
    }
    fun refreshEditImageFragment() {
        val findFragment = supportFragmentManager.findFragmentByTag(EditImageFragment.TAG)
        findFragment?.let { (it as EditImageFragment).reFresh() }
    }

}