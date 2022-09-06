package com.jdm.trustme.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MainDispatcher

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {
   @DefaultDispatcher
   @Provides
   fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

   @IoDispatcher
   @Provides
   fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

   @MainDispatcher
   @Provides
   fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}