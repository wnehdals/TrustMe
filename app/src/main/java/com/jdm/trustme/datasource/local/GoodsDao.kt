package com.jdm.trustme.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jdm.trustme.model.entity.Goods
import com.jdm.trustme.model.entity.Store
import kotlinx.coroutines.flow.Flow
@Dao
interface GoodsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoods(store_: Goods)

    @Query("SELECT * FROM GOODS")
    fun selectAllGoods(): Flow<List<Goods>>

    @Query("SELECT * FROM GOODS WHERE id = :id_")
    fun selectGoods(id_: Int): Flow<Goods>

    @Query("SELECT * FROM GOODS WHERE storeId = :storeId_")
    fun selectStoreGoods(storeId_: Int): Flow<List<Goods>>
}