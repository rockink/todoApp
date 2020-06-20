package com.proAndroid.todoapp.service.images

import android.content.ContentResolver
import android.content.ContentUris
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import com.proAndroid.todoapp.ui.photoGallery.Photo
import javax.inject.Inject

class ImageResolver @Inject constructor(private val contentResolver: ContentResolver) {
    private val IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    private val projectedColumns = arrayOf(
        MediaStore.Images.Media._ID
    )

    private val imageLiveData = MutableLiveData<List<Photo>>()

    init {
        imageLiveData.postValue(getImages())
    }

    fun getImages(): List<Photo> {
        val cursor = contentResolver.query(
            IMAGE_URI,
            projectedColumns,
            null,
            null,
            null
        )

        cursor?:return emptyList()

        val photoList = mutableListOf<Photo>()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            val photo = Photo(
                id.toInt(),
                ContentUris.withAppendedId(IMAGE_URI, id).toString()
            )
            photoList.add(photo)
        }

        cursor.close()
        return photoList
    }
}