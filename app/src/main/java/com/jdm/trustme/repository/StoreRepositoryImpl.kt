package com.jdm.trustme.repository

import androidx.lifecycle.asLiveData
import com.jdm.trustme.datasource.local.LocalDataSource
import com.jdm.trustme.model.entity.Store
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource): StoreRepository {
    override fun getStoreList(): Flow<MutableList<Store>> {
        return localDataSource.getAllStoreEntity().flatMapLatest {
            val storeList = it.toMutableList()
            storeList.add(0, Store(-1,"새로운 상점 등록하기", ""))
            flow {
                emit(storeList)
            }
        }.catch {

        }
    }

    override suspend fun insertStore(store: Store) {
        return localDataSource.insertStoreEntity(store)
    }
}