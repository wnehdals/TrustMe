package com.jdm.trustme.util

import android.graphics.Bitmap
import android.graphics.Matrix
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class RotateTransformation(angle: Float): BitmapTransformation() {
    private var angle = 0f
    init {
        this.angle = angle % 360
    }
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(("ratate${angle}").toByte())
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(toTransform, 0, 0, toTransform.width, toTransform.height, matrix, true)
    }
    fun getAngle(): Float = angle
    fun setAngle(angle: Float) {
        this.angle = angle % 360
    }
}