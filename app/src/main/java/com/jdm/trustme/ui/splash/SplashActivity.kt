package com.jdm.trustme.ui.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.jdm.trustme.R
import com.jdm.trustme.base.BaseActivity
import com.jdm.trustme.databinding.ActivitySplashBinding
import com.jdm.trustme.ui.main.MainActivity
import com.jdm.trustme.ui.write.ImagePickFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_splash

    private val storagePermissions = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
    } else {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
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
            goToMainActivity()
        }

    }
    override fun initView() {
        if (isStoragePermissionGranted()) {
            goToMainActivity()
        } else {
            requestStoragePermission()
        }
    }

    override fun subscribe() {
    }

    override fun initEvent() {
    }
    fun isStoragePermissionGranted() = storagePermissions.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    fun requestStoragePermission() {
        requestStoragePermissionLauncher.launch(storagePermissions)
    }
    fun goToMainActivity() {
        Intent(this, MainActivity::class.java).also { startActivity(it) }
    }

}