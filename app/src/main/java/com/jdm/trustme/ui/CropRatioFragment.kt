package com.jdm.trustme.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.jdm.trustme.R
import com.jdm.trustme.base.BaseFragment
import com.jdm.trustme.databinding.FragmentCropRatioBinding

class CropRatioFragment : BaseFragment<FragmentCropRatioBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_crop_ratio
    val viewModel: CropOptionViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backButtonCallBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                popBackFragment()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backButtonCallBack)
    }
    override fun initView() {
        viewModel.getBitmap()?.let {
            binding.cropRatioIv.setImageBitmap(it)
            binding.cropRatioIv.setAspectRatio(1,1)
        }
    }

    override fun initEvent() {
        with(binding) {
            cropRatioBackButton.setOnClickListener {
                popBackFragment()
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
                viewModel.setBitmap(img)
                popBackFragment()
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