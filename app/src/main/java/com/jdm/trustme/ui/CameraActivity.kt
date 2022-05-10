package com.jdm.trustme.ui

import android.Manifest.permission.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jdm.trustme.R
import com.jdm.trustme.base.BaseActivity
import com.jdm.trustme.const.PICTURE_URI
import com.jdm.trustme.databinding.ActivityCameraBinding
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
@AndroidEntryPoint
class CameraActivity : BaseActivity<ActivityCameraBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_camera
    private var imageCapture: ImageCapture? = null

    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null
    private var cameraExecutor: ExecutorService? = null
    private var camera: Camera? = null
    override fun subscribe() {
    }

    override fun initEvent() {
        with(binding) {
            imageCaptureButton.setOnClickListener {
                takePhoto()
            }
        }
    }

    override fun initView() {
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }
    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.KOREA).format(System.currentTimeMillis())
        Log.e(TAG, name)
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/TrustMe-Image")
            }
        }
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            .build()
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val msg = "photo capture succedd: ${outputFileResults.savedUri}"
                /*
                Intent(this@CameraActivity, GalleryActivity::class.java).also {
                    it.putExtra(PICTURE_URI, outputFileResults.savedUri)
                    this@CameraActivity.startActivity(it)
                }

                 */
                cropImage(outputFileResults.savedUri)
                Log.e(TAG, msg)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e(TAG, "take picture error")
            }
        })
    }
    private fun cropImage(uri: Uri?) {
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result: CropImage.ActivityResult = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri: Uri = result.uri
                goToGalleryActivity(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Log.e(TAG, result.error.toString())
            }
        }
    }
    private fun goToGalleryActivity(uri: Uri) {
        Intent(this@CameraActivity, GalleryActivity::class.java).also {
            it.putExtra(PICTURE_URI, uri)
            this@CameraActivity.startActivity(it)
        }
    }
    private fun captureVideo() {}

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

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
                observeCameraState(camera?.cameraInfo!!)
            } catch (exc: Exception) {
                Log.e(TAG, exc.message.toString())
            }
        }, ContextCompat.getMainExecutor(this))
    }
    private fun observeCameraState(cameraInfo: CameraInfo) {
        cameraInfo.cameraState.observe(this) { cameraState ->
            run {
                when (cameraState.type) {
                    CameraState.Type.PENDING_OPEN -> {
                        Log.e(TAG, "pending open")
                    }
                    CameraState.Type.OPENING -> {
                        Log.e(TAG, "opening")
                    }
                    CameraState.Type.OPEN -> {
                        Log.e(TAG, "open")
                    }
                    CameraState.Type.CLOSING -> {
                        Log.e(TAG, "closing")
                    }
                    CameraState.Type.CLOSED -> {
                        Log.e(TAG, "closed")
                    }
                }
            }
            cameraState.error?.let { error ->
                when (error.code) {
                    CameraState.ERROR_STREAM_CONFIG -> {
                        Log.e(TAG, "stream config")
                    }
                    CameraState.ERROR_CAMERA_IN_USE -> {
                        Log.e(TAG, "camera in use")
                    }
                    CameraState.ERROR_MAX_CAMERAS_IN_USE -> {
                        Log.e(TAG, "max camera in use")
                    }
                    CameraState.ERROR_OTHER_RECOVERABLE_ERROR -> {
                        Log.e(TAG, "other recoverable error")
                    }
                    CameraState.ERROR_CAMERA_DISABLED -> {
                        Log.e(TAG, "camera disabled")
                    }
                    CameraState.ERROR_CAMERA_FATAL_ERROR -> {
                        Log.e(TAG, "camera fatal error")
                    }
                    CameraState.ERROR_DO_NOT_DISTURB_MODE_ENABLED -> {
                        Log.e(TAG, "do not disturbe mode enabled")
                    }
                }
            }

        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    R.string.permissions_not_granted_message,
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG,"onDestroy")
        cameraExecutor?.shutdown()
        cameraExecutor = null
    }

    companion object {
        private const val TAG = "CameraActivity"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                CAMERA,
                RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()

    }
}