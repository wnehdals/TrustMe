package com.jdm.trustme.di

import android.content.Context
import androidx.room.Room
import com.jdm.trustme.datasource.local.AppDatabase
import com.jdm.trustme.datasource.local.FoodDao
import com.jdm.trustme.datasource.local.StoreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "TrustMe.db")
            .build()
    }
    @Provides
    @Singleton
    fun provideStoreDao(appAppDatabase: AppDatabase): StoreDao {
        return appAppDatabase.storeDao()
    }

    @Provides
    @Singleton
    fun provideGoodsDao(appAppDatabase: AppDatabase): FoodDao {
        return appAppDatabase.goodsDao()
    }
}