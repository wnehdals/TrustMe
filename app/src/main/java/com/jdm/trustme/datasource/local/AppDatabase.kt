package com.jdm.trustme.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jdm.trustme.model.entity.Goods
import com.jdm.trustme.model.entity.Store
import com.jdm.trustme.util.GoodsListConverter
import com.jdm.trustme.util.StoreListConverter

@Database(
    entities = [Store::class, Goods::class], version = 1
)
@TypeConverters(
    value = [
        (StoreListConverter::class), (GoodsListConverter::class)
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun storeDao(): StoreDao
    abstract fun goodsDao(): GoodsDao
}