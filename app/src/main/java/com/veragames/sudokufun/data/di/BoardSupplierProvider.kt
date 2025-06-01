package com.veragames.sudokufun.data.di

import com.veragames.sudokufun.data.board.BoardSupplier
import com.veragames.sudokufun.data.board.JsonBoardSupplier
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BoardSupplierProvider {
    @Binds
    @Singleton
    abstract fun getBoardSupplier(impl: JsonBoardSupplier): BoardSupplier
}
