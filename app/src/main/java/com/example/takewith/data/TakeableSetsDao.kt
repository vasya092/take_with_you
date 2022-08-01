package com.example.takewith.data

import androidx.room.Dao
import androidx.room.Query
import com.example.takewith.model.TakeableSet
import kotlinx.coroutines.flow.Flow

@Dao
interface TakeableSetsDao {
    @Query("SELECT * from takeable_sets")
    fun getAllTakeableSets() : Flow<List<TakeableSet>>
}