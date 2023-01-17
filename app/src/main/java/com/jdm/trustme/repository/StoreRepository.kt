package com.jdm.trustme.repository

import com.jdm.data.model.entity.Store
import com.jdm.data.model.entity.response.Feed
import com.jdm.trustme.util.Type
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    fun getStoreList(type: Type): Flow<List<Store>>
    suspend fun insertStore(store: Store)
    suspend fun insertFood(storeId: Long, name: String, content: String, imgList: MutableList<Long>)
    fun getFeedList(): Flow<MutableList<Feed>>
}