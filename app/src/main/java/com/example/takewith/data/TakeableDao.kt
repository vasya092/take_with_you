package com.example.takewith.data

import androidx.room.*
import com.example.takewith.model.TakeableItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TakeableDao {

    @Query("SELECT * from takeable_item")
    fun getAllTakeableItems(): Flow<List<TakeableItem>>

    @Query("SELECT * from takeable_item WHERE id = :id")
    fun getTakeableItem(id: Long) : Flow<TakeableItem>

    @Insert
    suspend fun insert(takeableItem: TakeableItem)

    @Update
    suspend fun update(takeableItem: TakeableItem)

    @Delete
    suspend fun delete(takeableItem: TakeableItem)

}