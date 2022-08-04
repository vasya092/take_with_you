package com.example.takewith.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "takeable_item")
data class TakeableItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "count")
    val count: Int?,
    @ColumnInfo(name = "set_id")
    val setId: Long = 0
)