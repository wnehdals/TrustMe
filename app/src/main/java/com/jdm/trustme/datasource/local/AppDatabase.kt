package com.jdm.trustme.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jdm.trustme.model.entity.Food
import com.jdm.trustme.model.entity.Store
import com.jdm.trustme.util.FoodListConverter
import com.jdm.trustme.util.StoreListConverter
import com.jdm.trustme.util.LongListConverter
import com.jdm.trustme.util.UriListConverter

@Database(
    entities = [Store::class, Food::class], version = 1
)
@TypeConverters(
    value = [
        (StoreListConverter::class), (FoodListConverter::class), (LongListConverter::class), (UriListConverter::class)
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun storeDao(): StoreDao
    abstract fun goodsDao(): FoodDao
}