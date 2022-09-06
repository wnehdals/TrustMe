package com.jdm.trustme.ui.write

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.jdm.trustme.R
import com.jdm.trustme.base.BaseActivity
import com.jdm.trustme.databinding.ActivityWriteBinding
import com.jdm.trustme.ui.CropRatioFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteActivity : BaseActivity<ActivityWriteBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_write
    private val cameraPermissions = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
    } else {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
    }
    private val storagePermissions = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
    } else {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private val requestCameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        var permissionFlag = true
        for (entry in it.entries) {
            if (!entry.value) {
                permissionFlag = false
                break
            }
        }
        if (!permissionFlag) {
            Toast.makeText(this, R.string.permissions_not_granted_message, Toast.LENGTH_SHORT).show()
            finish()
        } else {
            //addFragment()
        }
    }
    private val requestStoragePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        var permissionFlag = true
        for (entry in it.entries) {
            if (!entry.value) {
                permissionFlag = false
                break
            }
        }
        if (!permissionFlag) {
            Toast.makeText(this, R.string.permissions_storage_not_granted_message, Toast.LENGTH_SHORT).show()
            finish()
        } else {
            addFragment(ImagePickFragment.TAG)
        }

    }
    private val viewModel: WriteViewModel by viewModels()

    override fun subscribe() {
    }

    override fun initView() {
        addFragment(SelectStoreFragment.TAG)
    }
    override fun initEvent() {

    }
    fun requestCameraPermission() {
        requestCameraPermissionLauncher.launch(cameraPermissions)
    }
    fun requestStoragePermission() {
        requestStoragePermissionLauncher.launch(storagePermissions)
    }
    fun isCameraPermissionGranted() = cameraPermissions.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    fun isStoragePermissionGranted() = storagePermissions.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    fun backPressedFragment(deleteTag: String, showTag: String = "") {
        if (showTag !=  "") {
            var findFragment = supportFragmentManager.findFragmentByTag(showTag)
            findFragment?.let {
                supportFragmentManager.beginTransaction().show(it).commitAllowingStateLoss()
                if (showTag == WriteFragment.TAG) {
                    (findFragment as WriteFragment).refresh()
                }
            }
        }
        var findFragment = supportFragmentManager.findFragmentByTag(deleteTag)
        findFragment?.let { supportFragmentManager.beginTransaction().remove(it).commitAllowingStateLoss() }
    }
    fun addFragment(tag: String) {
        supportFragmentManager.fragments.forEach { supportFragmentManager.beginTransaction().hide(it).commitAllowingStateLoss() }
        when (tag) {
            SelectStoreFragment.TAG -> supportFragmentManager.beginTransaction().add(R.id.writeFragmentContainer, SelectStoreFragment.newInstance(), SelectStoreFragment.TAG).commitAllowingStateLoss()
            WriteFragment.TAG -> supportFragmentManager.beginTransaction().add(R.id.writeFragmentContainer, WriteFragment.newInstance(), WriteFragment.TAG).commitAllowingStateLoss()
            ImagePickFragment.TAG -> {
                supportFragmentManager.beginTransaction().add(R.id.writeFragmentContainer, ImagePickFragment.newInstance(), ImagePickFragment.TAG).commitAllowingStateLoss()
            }
            EditImageFragment.TAG -> supportFragmentManager.beginTransaction().add(R.id.writeFragmentContainer, EditImageFragment.newInstance(), EditImageFragment.TAG).commitAllowingStateLoss()
            CropRatioFragment.TAG -> supportFragmentManager.beginTransaction().add(R.id.writeFragmentContainer, CropRatioFragment.newInstance(), CropRatioFragment.TAG).commitAllowingStateLoss()
            else -> return
        }
    }
    fun goToImagePickActivity() {
    }
    companion object {
        const val TAG = "WriteActivity"
        const val REQUEST_CAMERA_CODE_PERMISSIONS = 10
    }

}