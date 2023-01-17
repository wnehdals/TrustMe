package com.jdm.trustme.repository

import com.jdm.data.local.LocalDataSource
import com.jdm.data.model.entity.Food
import com.jdm.data.model.entity.Store
import com.jdm.data.model.entity.response.Feed
import com.jdm.trustme.util.Type
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource): StoreRepository {
    override fun getStoreList(type: Type): Flow<MutableList<Store>> {
        return localDataSource.getAllStoreEntity().flatMapLatest {
            val storeList = it.toMutableList()
            if (type == Type.WRITE)
                storeList.add(0, Store(-1,"새로운 상점 등록하기", 0))
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

    override fun getFeedList(): Flow<MutableList<Feed>> {
        return localDataSource.getAllFoodEntity()
            .flatMapLatest {
                val feedList = mutableListOf<Feed>()
                it.forEach {
                    val store = localDataSource.getStoreEntity(it.storeId)
                    feedList.add(Feed(it, store))
                }
                flow { emit(feedList) }
            }
    }
}