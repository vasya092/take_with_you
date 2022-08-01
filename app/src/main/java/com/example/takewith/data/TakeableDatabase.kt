package com.example.takewith.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.takewith.model.TakeableItem
import com.example.takewith.model.TakeableSet


@Database(entities = [TakeableItem::class, TakeableSet::class], version = 3, exportSchema = false)
abstract class TakeableDatabase : RoomDatabase() {

    abstract fun takeableDao(): TakeableDao
    abstract fun takeableSetsDao(): TakeableSetsDao

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
