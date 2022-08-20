package com.jdm.trustme.model.media

import android.net.Uri

data class Media(
    val albumName: String,
    val uri: Uri,
    val dateAddedSecond: Long
)