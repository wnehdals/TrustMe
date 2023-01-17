package com.jdm.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jdm.data.model.entity.Store
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStore(store_: Store)

    @Query("SELECT * FROM STORE")
    fun selectAllStore(): Flow<List<Store>>

    @Query("SELECT * FROM STORE WHERE id = :id_")
    suspend fun selectStore(id_: Long): Store
}