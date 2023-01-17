package com.jdm.data.local

import com.jdm.data.model.entity.Food
import com.jdm.data.model.entity.Store
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val storeDao: StoreDao,
    private val foodDao: FoodDao,
    private val ioDispatcher: CoroutineDispatcher
) {
    fun getAllStoreEntity(): Flow<List<Store>> {
        return storeDao.selectAllStore().flowOn(ioDispatcher)
    }
    suspend fun insertStoreEntity(store: Store) {
        storeDao.insertStore(store)
    }
    suspend fun insertFoodEntity(food: Food) {
        foodDao.insertFood(food)
    }
    fun getAllFoodEntity(): Flow<List<Food>> {
        return foodDao.selectAllFood().flowOn(ioDispatcher)
    }
    suspend fun getStoreEntity(id: Long): Store {
        return storeDao.selectStore(id)
    }
    /*
    fun getFoodEntity(): Flow<Food> = flow {
        foodDao.selectAllFood().collect {
            it.forEach { emit(it) }
        }
    }.flowOn(ioDispatcher)

     */
}