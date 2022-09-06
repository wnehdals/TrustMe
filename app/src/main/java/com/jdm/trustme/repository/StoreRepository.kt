package com.jdm.trustme.repository

import com.jdm.trustme.model.entity.Store
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    fun getStoreList(): Flow<List<Store>>
    suspend fun insertStore(store: Store)
}