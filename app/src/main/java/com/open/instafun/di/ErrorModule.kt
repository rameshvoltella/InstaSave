package com.open.instafun.di



import com.open.instafun.errors.mapper.ErrorMapper
import com.open.instafun.errors.mapper.ErrorMapperSource
import com.open.instafun.errors.ErrorManager
import com.open.instafun.errors.ErrorUseCase

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// with @Module we Telling Dagger that, this is a Dagger module
@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {

    @Binds
    @Singleton
    abstract fun provideErrorFactoryImpl(errorManager: ErrorManager): ErrorUseCase

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper): ErrorMapperSource
}