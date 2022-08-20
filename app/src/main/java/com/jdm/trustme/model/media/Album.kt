package com.jdm.trustme.model.media

import android.net.Uri

internal data class Album(
    val name: String,
    val thumbnailUri: Uri,
    val mediaUris: MutableList<Media>
) {
    val mediaCount: Int = mediaUris.size
}