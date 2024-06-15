package com.bangkit.eyetify.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "results")
data class ResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val result: String,
    val suggestion: String,
    val imageUri: String
)
