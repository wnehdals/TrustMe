package com.jdm.trustme.di

import com.jdm.trustme.datasource.local.LocalDataSource
import com.jdm.trustme.repository.StoreRepository
import com.jdm.trustme.repository.StoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideStoreRepository(localDataSource: LocalDataSource): StoreRepository {
        return StoreRepositoryImpl(localDataSource)
    }
}