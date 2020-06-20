package com.proAndroid.todoapp.ui.photoGallery

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proAndroid.todoapp.service.images.ImageService
import javax.inject.Inject

data class Photo(
    val id: Int,
    val uri: String
)


class PhotoViewModel(private val imageService: ImageService) : ViewModel() {
    fun reloadImages() {
        imageService.reloadImages()
    }

    val photos = Transformations.map(imageService.getAllPhotos()) { it }
}


class PhotoViewModelFactory @Inject constructor(val imageService: ImageService) : ViewModelProvider.Factory {
override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
        return PhotoViewModel(imageService) as T
    }
    throw RuntimeException("${modelClass.canonicalName} is not assignable from " +
            "${PhotoViewModel::class.java.canonicalName}")
}

}