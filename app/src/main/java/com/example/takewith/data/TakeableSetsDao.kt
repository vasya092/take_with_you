package com.example.takewith.data

import androidx.room.*
import com.example.takewith.model.TakeableSet
import kotlinx.coroutines.flow.Flow

@Dao
interface TakeableSetsDao {
    @Query("SELECT * from takeable_sets")
    fun getAllTakeableSets() : Flow<List<TakeableSet>>

    @Query("DELETE FROM takeable_sets WHERE id = :id")
    suspend fun deleteTakeableSetById(id: Long)

    @Query("SELECT * FROM takeable_sets WHERE id = :id")
    fun getTakeableSetById(id: Long): Flow<TakeableSet>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(takeableSet: TakeableSet)

    @Update
    suspend fun update(takeableSet: TakeableSet)

    @Delete
    suspend fun delete(takeableSet: TakeableSet)
}