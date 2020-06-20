package com.proAndroid.todoapp.service.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.proAndroid.todoapp.ui.photoGallery.Photo
import javax.inject.Inject

class ImageService @Inject constructor(private val imageResolver: ImageResolver) {
    private val photoLiveData = MutableLiveData<List<Photo>>()
    private val photos = getTodoImages()

    init {
        photoLiveData.postValue(photos)
    }

    fun getAllPhotos(): LiveData<List<Photo>> {
        val allImages = getTodoImages() + imageResolver.getImages()
        photoLiveData.postValue(allImages)
        return photoLiveData
    }


    fun getTodoImages(): List<Photo> {
        val urlTemplate = "https://picsum.photos/id/%d/200/300" // d as in integer, decimal
        val ids = listOf(1001, 1002, 1003, 1004)

        return ids
            .map { urlTemplate.format(it) }
            .mapIndexed{index, uri -> Photo(index, uri)}
    }

    fun reloadImages() {
        getAllPhotos()
    }


}