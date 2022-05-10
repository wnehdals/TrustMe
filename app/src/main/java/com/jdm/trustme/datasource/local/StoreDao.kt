package com.jdm.trustme.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jdm.trustme.model.entity.Store
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStore(store_: Store)

    @Query("SELECT * FROM STORE")
    fun selectAllStore(): Flow<List<Store>>

    @Query("SELECT * FROM STORE WHERE id = :id_")
    fun selectStore(id_: Int): Flow<Store>
}