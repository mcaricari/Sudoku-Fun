package com.veragames.sudokufun.data.di

import com.veragames.sudokufun.data.preferences.DataStoreRepository
import com.veragames.sudokufun.data.preferences.DataStoreRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {
    @Binds
    @Singleton
    abstract fun bindDataStoreRepository(impl: DataStoreRepositoryImpl): DataStoreRepository
}
