package com.jdm.trustme.repository

import android.net.Uri
import com.jdm.trustme.model.entity.Store
import com.jdm.trustme.model.response.Feed
import com.jdm.trustme.util.Type
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    fun getStoreList(type: Type): Flow<List<Store>>
    suspend fun insertStore(store: Store)
    suspend fun insertFood(storeId: Long, name: String, content: String, imgList: MutableList<Long>)
    fun getFeedList(): Flow<MutableList<Feed>>
}