package com.jdm.trustme

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.storage.StorageManager
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.jdm.trustme.base.BaseActivity
import com.jdm.trustme.databinding.ActivityMainBinding
import com.jdm.trustme.ui.CameraActivity
import com.jdm.trustme.ui.ImagePickActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.function.BinaryOperator

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main

    private val permissions = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
    } else {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    private val requestMultiplePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            var permissionFlag = true
            for (entry in it.entries) {
                if (!entry.value) {
                    permissionFlag = false
                    break
                }
            }
            if (!permissionFlag) {
                Toast.makeText(this, getString(R.string.permissions_storage_not_granted_message), Toast.LENGTH_SHORT)
                    .show()
                finish()
            } else {
                goToImagePickActivity()
            }
        }

    override fun subscribe() {


    }

    override fun initView() {
    }


    override fun initEvent() {
        with(binding) {
            takePictureButton.setOnClickListener {
                Intent(this@MainActivity, CameraActivity::class.java)
                    .run { startActivity(this) }
            }
            openImgButton.setOnClickListener {

                step1Check(this@MainActivity)
            }
        }
    }
    private fun step1Check (context: Context) {
        if (enableStorage(context)) {
            goToImagePickActivity()
        } else {
            requestMultiplePermissionLauncher.launch(permissions)
        }
    }
    private fun enableStorage(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return isEnableWriteExteranlStorage(context)
        } else {
            return isEnableWriteExteranlStorage(context) && isEnableReadExternalStorage(context)
        }
    }
    private fun isEnableReadExternalStorage(context: Context): Boolean {
        return context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }
    private fun isEnableWriteExteranlStorage(context: Context): Boolean {
        return context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }
    private fun goToImagePickActivity() {
        Intent(this, ImagePickActivity::class.java).also { startActivity(it) }
    }
}