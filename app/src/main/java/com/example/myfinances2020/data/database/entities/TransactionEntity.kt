package com.example.myfinances2020.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val _id: Long = 0L,

    @ColumnInfo(name = "transaction_day")
    val day: Int = 0,

    @ColumnInfo(name = "transaction_month")
    val month: Int = 0,

    @ColumnInfo(name = "transaction_year")
    val year: Int = 0,

    @ColumnInfo(name = "transaction_category")
    val category: String = "",

    @ColumnInfo(name = "transaction_comment")
    val comment: String? = "",

    @ColumnInfo(name = "transaction_amount")
    val amount: Double = 0.0,

    @ColumnInfo(name = "is_expense")
    val isExpense: Boolean = true
)
