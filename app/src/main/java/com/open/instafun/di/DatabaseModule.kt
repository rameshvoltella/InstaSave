package com.open.instafun.di
import android.content.Context
import androidx.room.Room
import com.open.instafun.database.InstagramDatabase
import com.open.instafun.database.InstagramDownloaderDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): InstagramDatabase {
        return Room.databaseBuilder(
            context,
            InstagramDatabase::class.java,
            "instagram_database"
        ).build()
    }

    @Provides
    fun providePlaylistDao(database: InstagramDatabase): InstagramDownloaderDao {
        return database.instagramDownloaderDao()
    }


}
