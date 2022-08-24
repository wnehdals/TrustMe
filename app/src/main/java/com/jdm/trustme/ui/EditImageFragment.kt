package com.jdm.trustme.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jdm.trustme.R
import com.jdm.trustme.base.BaseFragment
import com.jdm.trustme.const.PICTURE_URI
import com.jdm.trustme.databinding.FragmentEditImageBinding
import com.jdm.trustme.util.GalleryUtil
import com.jdm.trustme.util.RotateTransformation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditImageFragment : BaseFragment<FragmentEditImageBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_edit_image
    val viewModel: CropOptionViewModel by activityViewModels()
    val rotateTransformation = RotateTransformation(0f)
    private var angle = 0f
    override fun onAttach(context: Context) {
        super.onAttach(context)
        backButtonCallBack = object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backButtonCallBack)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun initView() {
        angle = 0f
        viewModel.getBitmap()?.let { binding.cropOptionImg.setImageBitmap(it) }
    }
    fun reFresh() {
        angle = 0f
        Log.e("jdm_tag", "onstart")
        viewModel.getBitmap()?.let { binding.cropOptionImg.setImageBitmap(it) }
    }


    override fun initEvent() {
        with(binding) {
            cropOptionBackButton.setOnClickListener {
                requireActivity().finish()
            }
            cropOptionCompleteButton.setOnClickListener {
                GalleryUtil.saveBitmap(requireContext(), viewModel.getBitmap()!!)
            }
            cropOptionButton.setOnClickListener {
                goToCropRatioFragment()
            }
            cropOptionTv.setOnClickListener {
                goToCropRatioFragment()
            }
            rotateOptionButton.setOnClickListener {
                Log.e("jdm_tag", "clickclick")
                rotateImg()
            }
            rotateOptionTv.setOnClickListener {
                rotateImg()
            }
        }
    }
    fun rotateImg() {
        angle+=90f
        viewModel.getBitmap()?.let {
            val rotateImg = viewModel.rotateBitmap(it, angle)
            binding.cropOptionImg.setImageBitmap(rotateImg)
        }
    }
    fun uriToBitmap(uri: Uri, options: RequestOptions? = null) {
        if (options == null) {
            Glide.with(this).asBitmap().load(uri).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    viewModel.setBitmap(resource)
                    viewModel.getBitmap()?.let {
                        binding.cropOptionImg.setImageBitmap(it)
                    }

                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        } else {
            Glide.with(this).asBitmap().apply(options).load(uri).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    viewModel.setBitmap(resource)
                    viewModel.getBitmap()?.let {
                        binding.cropOptionImg.setImageBitmap(it)
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        }
    }
    override fun subscribe() {
    }
    private fun goToCropRatioFragment() {
        (requireActivity() as CropOptionActivity).addCropRatioFragment()
    }

    companion object {
        @JvmStatic
        fun newInstance() = EditImageFragment()
        const val TAG = "EditImageFragment"
    }
}