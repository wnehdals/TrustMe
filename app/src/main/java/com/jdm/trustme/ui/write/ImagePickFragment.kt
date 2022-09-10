package com.jdm.trustme.ui.write

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jdm.trustme.R
import com.jdm.trustme.base.BaseFragment
import com.jdm.trustme.databinding.FragmentImagePickBinding
import com.jdm.trustme.model.response.Gallery
import com.jdm.trustme.ui.write.adapter.ImagePickAdapter
import com.jdm.trustme.util.GalleryUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ImagePickFragment : BaseFragment<FragmentImagePickBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_image_pick
    private val imagePickAdapter by lazy {
        ImagePickAdapter(
            requireContext(),
            viewModel,
            this::goToCropOptionFragment
        )
    }
    private val viewModel: WriteViewModel by activityViewModels()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        backButtonCallBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (requireActivity() as WriteActivity).backPressedFragment(
                    ImagePickFragment.TAG,
                    WriteFragment.TAG
                )
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backButtonCallBack)
    }

    override fun initView() {
        viewModel.selectedGallery.clear()
        getImageList()
    }

    override fun initEvent() {
        with(binding) {
            imagePickBackButton.setOnClickListener {
                (requireActivity() as WriteActivity).backPressedFragment(
                    ImagePickFragment.TAG,
                    WriteFragment.TAG
                )
            }
            imagePickCompleteButton.setOnClickListener {
                (requireActivity() as WriteActivity).backPressedFragment(
                    ImagePickFragment.TAG,
                    WriteFragment.TAG
                )
            }
            imagePickRv.adapter = imagePickAdapter
        }
    }

    override fun subscribe() {
    }

    private fun goToCropOptionFragment(item: Gallery, position: Int) {
        viewModel.selectedGalleryPosition = position
        uriToBitmap(item.uri)
    }
    private fun getImageList() {
        GalleryUtil.getMedia(requireContext())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ newList ->
                imagePickAdapter.addData(newList)
            }, {})
    }

    fun isSameId(new: Gallery, oldList: MutableList<Gallery>): Boolean {
        var result = false
        for (old in oldList) {
            if (new.id == old.id) {
                result = true
                break
            }
        }

        return result
    }

    fun uriToBitmap(uri: Uri) {
        Glide.with(this).asBitmap().load(uri).into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                viewModel.bitmap = resource
                (requireActivity() as WriteActivity).addFragment(EditImageFragment.TAG)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })
    }



    companion object {
        @JvmStatic
        fun newInstance() = ImagePickFragment()
        const val TAG = "ImagePickFragment"
    }
}