package com.jdm.trustme.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jdm.trustme.model.entity.Food
import kotlinx.coroutines.flow.Flow
@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(food_: Food)

    @Query("SELECT * FROM FOOD")
    fun selectAllFood(): Flow<List<Food>>

    @Query("SELECT * FROM FOOD WHERE id = :id_")
    fun selectFood(id_: Int): Flow<Food>

    @Query("SELECT * FROM FOOD WHERE storeId = :storeId_")
    fun selectStoreFood(storeId_: Int): Flow<List<Food>>

}