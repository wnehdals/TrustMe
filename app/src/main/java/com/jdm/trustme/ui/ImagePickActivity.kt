package com.jdm.trustme.ui

import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.jdm.trustme.R
import com.jdm.trustme.base.BaseActivity
import com.jdm.trustme.const.GALLERY
import com.jdm.trustme.const.PICTURE_URI
import com.jdm.trustme.databinding.ActivityImagePickBinding
import com.jdm.trustme.model.entity.Gallery
import com.jdm.trustme.model.media.Album
import com.jdm.trustme.model.media.Media
import com.jdm.trustme.util.GalleryUtil
import gun0912.tedimagepicker.builder.type.MediaType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File

class ImagePickActivity : BaseActivity<ActivityImagePickBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_image_pick
    private val imagePickAdapter by lazy { ImagePickAdapter(this, this::goToCropOptionActivity) }
    override fun subscribe() {
    }

    override fun initEvent() {
    }


    override fun initView() {
        binding.imagePickRv.adapter = imagePickAdapter
        /*
        val list = mutableListOf<Media>()
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.SIZE
        )
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
        val query = contentResolver.query(collection, projection, null, null, sortOrder)
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val uri = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val geturi = cursor.getString(uri)
                Log.e("jdm_tag", "${id} / ${geturi}")
                list.add(Media("", cursor.getMediaUri(),1))
            }
            imagePickAdapter.addData(list)
        }

         */
        GalleryUtil.getMedia(this, MediaType.IMAGE )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                imagePickAdapter.addData(it)
            }, {
                
            })
    }
    private fun goToCropOptionActivity(item: Gallery) {
        val intent = Intent(this, CropOptionActivity::class.java)
        intent.putExtra(PICTURE_URI, item.uri)
        startActivity(intent)
    }

}