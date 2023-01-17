package com.jdm.trustme.di

import com.jdm.data.local.FoodDao
import com.jdm.data.local.LocalDataSource
import com.jdm.data.local.StoreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideLocalDataSource(storeDao: StoreDao, goodsDao: FoodDao, @IoDispatcher ioDispatcher: CoroutineDispatcher): LocalDataSource {
        return LocalDataSource(storeDao, goodsDao, ioDispatcher)
    }
}