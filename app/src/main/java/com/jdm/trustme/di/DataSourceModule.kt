package com.jdm.trustme.di

import com.jdm.trustme.datasource.local.GoodsDao
import com.jdm.trustme.datasource.local.LocalDataSource
import com.jdm.trustme.datasource.local.StoreDao
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
    fun provideLocalDataSource(storeDao: StoreDao, goodsDao: GoodsDao, @IoDispatcher ioDispatcher: CoroutineDispatcher): LocalDataSource {
        return LocalDataSource(storeDao, goodsDao, ioDispatcher)
    }
}