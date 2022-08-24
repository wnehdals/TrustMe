package com.jdm.trustme.ui

import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide

class CropOptionViewModel: ViewModel() {
    private var bitmap: Bitmap? = null
    fun setBitmap(result: Bitmap) {
        bitmap = result
    }
    fun getBitmap(): Bitmap? {
        return bitmap
    }
    fun rotateBitmap(toTransform: Bitmap, angle: Float): Bitmap {
        var _angle = angle % 360
        val matrix = Matrix()
        matrix.postRotate(_angle)
        bitmap = Bitmap.createBitmap(toTransform, 0, 0, toTransform.width, toTransform.height, matrix, true)
        return bitmap!!
    }
}