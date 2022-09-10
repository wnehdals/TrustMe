package com.jdm.trustme.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.asLiveData
import com.jdm.trustme.datasource.local.LocalDataSource
import com.jdm.trustme.model.entity.Food
import com.jdm.trustme.model.entity.Store
import com.jdm.trustme.util.Type
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource): StoreRepository {
    override fun getStoreList(type: Type): Flow<MutableList<Store>> {
        return localDataSource.getAllStoreEntity().flatMapLatest {
            val storeList = it.toMutableList()
            if (type == Type.WRITE)
                storeList.add(0, Store(-1,"새로운 상점 등록하기", ""))
            flow {
                emit(storeList)
            }
        }.catch {

        }
    }

    override suspend fun insertStore(store: Store) {
        localDataSource.insertStoreEntity(store)
    }

    override suspend fun insertFood(
        storeId: Long,
        name: String,
        content: String,
        imgList: MutableList<Long>
    ) {
        localDataSource.insertFoodEntity(
            Food(
                id = 0L,
                storeId = storeId,
                name = name,
                content = content,
                imgId = imgList
            )
        )
    }
}