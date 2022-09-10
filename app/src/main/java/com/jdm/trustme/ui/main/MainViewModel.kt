package com.jdm.trustme.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdm.trustme.model.entity.Store
import com.jdm.trustme.repository.StoreRepository
import com.jdm.trustme.util.Type
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(private val storeRepository: StoreRepository): ViewModel() {
    private val _storeData = MutableLiveData<List<Store>>()
    val storeData: LiveData<List<Store>> get() = _storeData


    fun getAllStoreList() {
        viewModelScope.launch {
            storeRepository.getStoreList(Type.MAIN).collectLatest {
                _storeData.value = it
            }
        }
    }
}