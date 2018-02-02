package com.cdrussell.casterio.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context


@Database(
    version = 1, entities = [
        // list DB entities
        Task::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao


    // Singleton example from Google Samples -
    // https://github.com/googlesamples/android-architecture-components/blob/master/BasicRxJavaSampleKotlin/app/src/main/java/com/example/android/observability/persistence/UsersDatabase.kt

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java, "app-database"
            ).build()
    }

}