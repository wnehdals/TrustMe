package com.jdm.trustme.model.response

import android.graphics.Bitmap
import android.net.Uri

data class Gallery(
    val id: Long,
    val name: String,
    val size: Long,
    val type: String,
    val uri: Uri,
    val date: Long,
    var selectedNum: Int = 0,
    var bitmap: Bitmap? = null
)