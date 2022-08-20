package com.jdm.trustme.util

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.jdm.trustme.model.entity.Gallery
import com.jdm.trustme.model.media.Album
import com.jdm.trustme.model.media.Media
import gun0912.tedimagepicker.builder.type.MediaType
import io.reactivex.rxjava3.core.Single
import java.io.File

internal class GalleryUtil {
    companion object {

        private const val INDEX_MEDIA_ID = MediaStore.MediaColumns._ID
        private const val INDEX_MEDIA_URI = MediaStore.MediaColumns.DATA
        private const val INDEX_DATE_ADDED = MediaStore.MediaColumns.DATE_ADDED

        private lateinit var albumName: String

        internal fun getMedia(context: Context, mediaType: MediaType): Single<MutableList<Gallery>> {
            return Single.create { emitter ->
                try {

                    val galleryList = mutableListOf<Gallery>()
                    val collecton = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
                        } else {
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        }

                    val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

                    val projection = arrayOf(
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.MediaColumns.DATA,
                        MediaStore.Images.Media.SIZE,
                        MediaStore.Images.Media.DATE_ADDED,
                        MediaStore.Images.Media.MIME_TYPE
                    )
                    val selection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        MediaStore.Images.Media.SIZE + " > 0"
                    } else {
                        null
                    }
                    val query = context.contentResolver.query(collecton, projection, selection, null, sortOrder)
                    query?.use { cursor ->
                        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                        val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                        val uriColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                        val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                        val dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
                        val mineTypeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)
                        while (cursor.moveToNext()) {
                            val id = cursor.getLong(idColumn)
                            val name = cursor.getString(displayNameColumn)
                            val uri = getMediaUri(id,cursor.getString(uriColumn))
                            val size = cursor.getLong(sizeColumn)
                            val date = cursor.getLong(dateAddedColumn)
                            val type = cursor.getString(mineTypeColumn)
                            galleryList.add(Gallery(id, name, size, type, uri, date))
                        }
                    }
                    query?.close()
                    if (!galleryList.isEmpty()) {
                        emitter.onSuccess(galleryList)
                    } else {
                        val emptyList = mutableListOf<Gallery>()
                        emitter.onSuccess(emptyList)
                    }
                } catch (exception: Exception) {
                    emitter.onError(exception)
                }

            }
        }




        private fun getMediaUri(id: Long, str: String): Uri =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                ContentUris.withAppendedId(contentUri, id)
            } else {
                Uri.fromFile(File(str))
            }
    }
}