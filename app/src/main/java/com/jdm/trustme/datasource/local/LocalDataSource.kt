package com.jdm.trustme.datasource.local

import com.jdm.trustme.model.entity.Store
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val storeDao: StoreDao,
    private val goodsDao: GoodsDao,
    private val ioDispatcher: CoroutineDispatcher
) {
    fun getAllStoreEntity(): Flow<List<Store>> {
        return storeDao.selectAllStore().flowOn(ioDispatcher)
    }
    suspend fun insertStoreEntity(store: Store) {
        storeDao.insertStore(store)
    }
}