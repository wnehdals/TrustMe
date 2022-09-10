package com.jdm.trustme.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdm.trustme.model.entity.Store
import com.jdm.trustme.model.response.Feed
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

    private val _feedData = MutableLiveData<MutableList<Feed>>()
    val feedData: LiveData<MutableList<Feed>> get() = _feedData


    fun getAllStoreList() {
        viewModelScope.launch {
            storeRepository.getStoreList(Type.MAIN).collectLatest {
                _storeData.value = it
            }
        }
    }
    fun getFeedList() {
        viewModelScope.launch {
            storeRepository.getFeedList().collectLatest {
                _feedData.value = it
            }
        }
    }
}