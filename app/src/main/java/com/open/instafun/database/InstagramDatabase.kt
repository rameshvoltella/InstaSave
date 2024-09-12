package com.open.instafun.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [InstagramDBDownloader::class], version = 1)
abstract class InstagramDatabase : RoomDatabase() {

    abstract fun instagramDownloaderDao(): InstagramDownloaderDao

    companion object {
        @Volatile
        private var INSTANCE: InstagramDatabase? = null

        fun getDatabase(context: Context): InstagramDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InstagramDatabase::class.java,
                    "instagram_database"
                )
//                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Migration from version 1 to version 2
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add the new column for version 2
                database.execSQL("ALTER TABLE instagram_downloader ADD COLUMN reelsUrl TEXT NOT NULL DEFAULT ''")
            }
        }
    }
}
