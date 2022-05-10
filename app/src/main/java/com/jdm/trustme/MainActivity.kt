package com.jdm.trustme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jdm.trustme.base.BaseActivity
import com.jdm.trustme.databinding.ActivityMainBinding
import com.jdm.trustme.ui.CameraActivity
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun subscribe() {

    }

    override fun initEvent() {
        with(binding) {
            takePictureButton.setOnClickListener {
                Intent(this@MainActivity, CameraActivity::class.java)
                    .run { startActivity(this) }
            }
        }
    }
}