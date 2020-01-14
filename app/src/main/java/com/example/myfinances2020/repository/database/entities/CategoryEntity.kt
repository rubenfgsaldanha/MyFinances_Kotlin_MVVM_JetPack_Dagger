package com.example.myfinances2020.repository.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val _id: Long = 0L,

    @ColumnInfo(name = "label")
    val label: String,

    @ColumnInfo(name = "color")
    val color: Int = 0
)