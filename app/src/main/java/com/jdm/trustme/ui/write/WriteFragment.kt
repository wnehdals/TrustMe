package com.jdm.trustme.ui.write

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.jdm.trustme.R
import com.jdm.trustme.base.BaseFragment
import com.jdm.trustme.databinding.FragmentWriteBinding
import com.jdm.trustme.model.entity.Gallery
import com.jdm.trustme.ui.write.adapter.SelectedImageAdapter

class WriteFragment : BaseFragment<FragmentWriteBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_write
    private val viewModel: WriteViewModel by activityViewModels()
    private val selectedImageAdapter by lazy { SelectedImageAdapter(requireContext(), this::onClickCloseButton) }
    private val requestStoragePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        var permissionFlag = true
        for (entry in it.entries) {
            if (!entry.value) {
                permissionFlag = false
                break
            }
        }
        if (!permissionFlag) {
            Toast.makeText(requireContext(), R.string.permissions_storage_not_granted_message, Toast.LENGTH_SHORT).show()
            requireActivity().finish()
        } else {
        }

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        backButtonCallBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (requireActivity() as WriteActivity).backPressedFragment(WriteFragment.TAG, SelectStoreFragment.TAG)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backButtonCallBack)
    }

    override fun initView() {
        with(binding) {
            writeStoreName.text = viewModel.selectStore.name
            galleryRecyclerview.adapter = selectedImageAdapter
            refresh()
        }
    }
    fun refresh() {
        binding.galleryRecyclerview.visibility = View.VISIBLE
        selectedImageAdapter.addData(viewModel.selectedGallery)
    }
    override fun initEvent() {
        with(binding) {
            writeBackButton.setOnClickListener {
                (requireActivity() as WriteActivity).backPressedFragment(WriteFragment.TAG, SelectStoreFragment.TAG)
            }
            writeCameraButton.setOnClickListener {
                if ((requireActivity() as WriteActivity).isCameraPermissionGranted()) {

                } else {
                    (requireActivity() as WriteActivity).requestCameraPermission()
                }
            }
            writeGalleryButton.setOnClickListener {
                if ((requireActivity() as WriteActivity).isStoragePermissionGranted()) {
                    (requireActivity() as WriteActivity).addFragment(ImagePickFragment.TAG)
                } else {
                    (requireActivity() as WriteActivity).requestStoragePermission()
                }
            }
        }
    }
    fun onClickCloseButton(item: Gallery, position: Int) {
        Log.e("jdm_tag", position.toString())
        viewModel.selectedGallery.removeAt(position)
        selectedImageAdapter.removeDataAt(position)
        selectedImageAdapter.notifyItemRemoved(position)
    }

    override fun subscribe() {
    }

    companion object {
        @JvmStatic
        fun newInstance() = WriteFragment()
        const val TAG = "WriteFragment"
    }
}