package com.jdm.trustme.ui

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.jdm.trustme.R
import com.jdm.trustme.base.BaseFragment
import com.jdm.trustme.databinding.FragmentCropRatioBinding
import com.jdm.trustme.ui.write.*

class CropRatioFragment : BaseFragment<FragmentCropRatioBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_crop_ratio
    val viewModel: WriteViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backButtonCallBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (requireActivity() as WriteActivity).backPressedFragment(
                    CropRatioFragment.TAG,
                    EditImageFragment.TAG
                )
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backButtonCallBack)
    }
    override fun initView() {
        viewModel.bitmap?.let {
            binding.cropRatioIv.setImageBitmap(it)
            binding.cropRatioIv.setAspectRatio(1,1)
        }
    }

    override fun initEvent() {
        with(binding) {
            cropRatioBackButton.setOnClickListener {
                (requireActivity() as WriteActivity).backPressedFragment(
                    CropRatioFragment.TAG,
                    EditImageFragment.TAG
                )
            }
            cropRatioOneToOneLl.setOnClickListener {
                cropRatioIv.setAspectRatio(1,1)
            }
            cropRatioFourToThreeLl.setOnClickListener {
                cropRatioIv.setAspectRatio(4,3)
            }
            cropRatioThreeToFourLl.setOnClickListener {
                cropRatioIv.setAspectRatio(3,4)
            }
            cropRatioCompleteButton.setOnClickListener {
                val img = cropRatioIv.croppedImage
                viewModel.editSelectedGallery()
                (requireActivity() as WriteActivity).backPressedFragment(CropRatioFragment.TAG, "")
                (requireActivity() as WriteActivity).backPressedFragment(EditImageFragment.TAG, "")
                (requireActivity() as WriteActivity).backPressedFragment(ImagePickFragment.TAG, WriteFragment.TAG)

            }
        }
    }

    override fun subscribe() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as CropOptionActivity).refreshEditImageFragment()

    }
    fun popBackFragment() {
        parentFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CropRatioFragment()
        const val TAG = "CropRatioFragment"
    }
}