package com.jdm.trustme.model.entity

import android.net.Uri

data class Gallery(
    val id: Long,
    val name: String,
    val size: Long,
    val type: String,
    val uri: Uri,
    val date: Long
)