package com.jdm.trustme.ui.write

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jdm.trustme.R
import com.jdm.trustme.base.BaseDialog
import com.jdm.trustme.base.BaseFragment
import com.jdm.trustme.databinding.FragmentEditImageBinding
import com.jdm.trustme.ui.CropRatioFragment
import com.jdm.trustme.util.GalleryUtil
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.type.MediaType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class EditImageFragment : BaseFragment<FragmentEditImageBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_edit_image
    val viewModel: WriteViewModel by activityViewModels()
    private var angle = 0f
    override fun onAttach(context: Context) {
        super.onAttach(context)
        backButtonCallBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (requireActivity() as WriteActivity).backPressedFragment(TAG, ImagePickFragment.TAG)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backButtonCallBack)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun initView() {
        angle = 0f
        viewModel.bitmap?.let { binding.cropOptionImg.setImageBitmap(it) }
    }
    fun reFresh() {
        angle = 0f
        viewModel.bitmap?.let { binding.cropOptionImg.setImageBitmap(it) }
    }


    override fun initEvent() {
        with(binding) {
            cropOptionBackButton.setOnClickListener {
                BaseDialog.makeSimpleDialog(
                    context = requireContext(),
                    message = getString(R.string.str_check_img_edit_message),
                    positiveButtonText = getString(R.string.str_left),
                    negativeButtonText = getString(R.string.str_cancel),
                    positiveButtonOnClickListener = { dialog, _ ->
                        dialog.dismiss()
                        (requireActivity() as WriteActivity).backPressedFragment(TAG, ImagePickFragment.TAG)
                    },
                    negativeButtonOnClickListener = { dialog, _ ->
                        dialog.dismiss()
                    },
                    cancelable = false
                )
                //(requireActivity() as WriteActivity).backPressedFragment(EditImageFragment.TAG, ImagePickFragment.TAG)
            }
            cropOptionCompleteButton.setOnClickListener {
                viewModel.editSelectedGallery()
                GalleryUtil.saveBitmap(requireContext(), viewModel.bitmap!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it) {
                            (requireActivity() as WriteActivity).backPressedFragment(TAG, "")
                            (requireActivity() as WriteActivity).backPressedFragment(ImagePickFragment.TAG, WriteFragment.TAG)
                        }
                    }, {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    })
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
            cropOptionBackButton.setOnClickListener {
                (requireActivity() as WriteActivity).backPressedFragment(TAG, ImagePickFragment.TAG)
            }
        }
    }
    fun getMedia() {
        GalleryUtil.getMedia(requireContext(), MediaType.IMAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
               Log.e("jdm_tag",it.size.toString())
            },{})
    }
    fun rotateImg() {
        angle+=90f
        viewModel.bitmap?.let {
            val rotateImg = viewModel.rotateBitmap(it, angle)
            binding.cropOptionImg.setImageBitmap(rotateImg)
        }
    }
    fun uriToBitmap(uri: Uri, options: RequestOptions? = null) {
        if (options == null) {
            Glide.with(this).asBitmap().load(uri).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    viewModel.bitmap = resource
                    viewModel.bitmap?.let {
                        binding.cropOptionImg.setImageBitmap(it)
                    }

                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        } else {
            Glide.with(this).asBitmap().apply(options).load(uri).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    viewModel.bitmap = resource
                    viewModel.bitmap?.let {
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
        (requireActivity() as WriteActivity).addFragment(CropRatioFragment.TAG)
    }

    companion object {
        @JvmStatic
        fun newInstance() = EditImageFragment()
        const val TAG = "EditImageFragment"
    }
}