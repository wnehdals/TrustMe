package com.jdm.trustme.ui.write

import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdm.trustme.model.response.Gallery
import com.jdm.trustme.model.entity.Store
import com.jdm.trustme.repository.StoreRepository
import com.jdm.trustme.util.Type
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
@HiltViewModel
class WriteViewModel@Inject constructor(private val storeRepository: StoreRepository): ViewModel() {
    private val _storeData = MutableLiveData<List<Store>>()
    val storeData: LiveData<List<Store>> get() = _storeData

    private val _completeButtonState = MutableLiveData<Boolean>()
    val completeButtonState: LiveData<Boolean> get() = _completeButtonState

    var selectStore = Store(-1L,"", 0)
    var bitmap: Bitmap? = null
    var selectedGallery = LinkedList<Gallery>()
    var selectedGalleryPosition = 0
    fun getAllStoreList() {
        viewModelScope.launch {
            storeRepository.getStoreList(Type.WRITE).collectLatest {
                _storeData.value = it
            }
        }
    }
    fun insertStore(store: Store) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                storeRepository.insertStore(store)
                getAllStoreList()
            }
        }
    }
    fun rotateBitmap(toTransform: Bitmap, angle: Float): Bitmap {
        var _angle = angle % 360
        val matrix = Matrix()
        matrix.postRotate(_angle)
        bitmap = Bitmap.createBitmap(toTransform, 0, 0, toTransform.width, toTransform.height, matrix, true)
        return bitmap!!
    }
    fun editSelectedGallery() {
        bitmap?.let {
            selectedGallery[selectedGalleryPosition].bitmap = it
        }
    }
    fun editSelectedGallery(gallery: Gallery) {
        selectedGallery[selectedGalleryPosition] = gallery
    }
    fun isAllowCompleteButton(title: String, content: String) {
        _completeButtonState.value = title.isNotEmpty() && content.isNotEmpty()
    }
    fun saveFood(title: String, content: String) {
        viewModelScope.launch {
            val imgList = mutableListOf<Long>()
            selectedGallery.forEach {
                imgList.add(it.id)
            }
            withContext(Dispatchers.IO){
                storeRepository.insertFood(selectStore.id, title, content, imgList)
            }

        }

    }

}