package com.open.instafun.di


import com.open.instafun.data.remote.RemoteRepository
import com.open.instafun.data.remote.RemoteRepositorySource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Tells Dagger this is a Dagger module
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {




    @Binds
    @Singleton
    abstract fun provideRemoteRepository(remoteRepository: RemoteRepository): RemoteRepositorySource


}