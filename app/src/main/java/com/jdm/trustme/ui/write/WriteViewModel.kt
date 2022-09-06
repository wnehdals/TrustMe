package com.jdm.trustme.ui.write

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdm.trustme.model.entity.Gallery
import com.jdm.trustme.model.entity.Store
import com.jdm.trustme.repository.StoreRepository
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

    var selectStore = Store(-1,"", "")
    var bitmap: Bitmap? = null
    var selectedGallery = LinkedList<Gallery>()
    var selectedGalleryPosition = 0
    fun getAllStoreList() {
        viewModelScope.launch {
            storeRepository.getStoreList().collectLatest {
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

}