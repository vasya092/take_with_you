package com.example.takewith.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.takewith.model.TakeableItem


@Database(entities = [TakeableItem::class], version = 1, exportSchema = false)
abstract class TakeableDatabase : RoomDatabase() {

    abstract fun takeableDao(): TakeableDao

    companion object {
        @Volatile
        private var INSTANCE: TakeableDatabase? = null

        fun getDatabase(context: Context) : TakeableDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TakeableDatabase::class.java,
                    "takeable_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
