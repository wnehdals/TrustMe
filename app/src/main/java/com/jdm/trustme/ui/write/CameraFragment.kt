package com.jdm.trustme.ui.write

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.provider.MediaStore
import androidx.activity.OnBackPressedCallback
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jdm.trustme.R
import com.jdm.trustme.base.BaseFragment
import com.jdm.trustme.databinding.FragmentCameraBinding
import com.jdm.trustme.model.response.Gallery
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
@AndroidEntryPoint
class CameraFragment : BaseFragment<FragmentCameraBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_camera
    val viewModel: WriteViewModel by activityViewModels()
    private var imageCapture: ImageCapture? = null
    private var cameraExecutor: ExecutorService? = null
    private var camera: Camera? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        backButtonCallBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (requireActivity() as WriteActivity).backPressedFragment(
                    TAG, WriteFragment.TAG
                )
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backButtonCallBack)
    }
    override fun initView() {
        startCamera()
    }

    override fun initEvent() {
        with(binding) {
            imageCaptureButton.setOnClickListener {
                takePhoto()
            }
        }
    }

    override fun subscribe() {
    }
    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.KOREA).format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/TrustMe-Image")
            }
        }
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(requireContext().contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            .build()
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(requireContext()), object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val msg = "photo capture succedd: ${outputFileResults.savedUri}"

                Glide.with(requireContext()).asBitmap().load(outputFileResults.savedUri).into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        viewModel.bitmap = resource
                        viewModel.selectedGallery.add(Gallery(-1,name,0L,"",outputFileResults.savedUri!!,0L,0,resource))
                        viewModel.selectedGalleryPosition = 0
                        viewModel.editSelectedGallery()
                        goToEditImageFragment()
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
            }

            override fun onError(exception: ImageCaptureException) {
            }
        })
    }
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }
    private fun goToEditImageFragment() {
        (requireActivity() as WriteActivity).addFragment(EditImageFragment.TAG)
    }

    companion object {
        @JvmStatic
        fun newInstance() = CameraFragment()
        const val TAG = "CameraFragment"
        const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm"
    }
}