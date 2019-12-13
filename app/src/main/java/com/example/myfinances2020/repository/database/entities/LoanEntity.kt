package com.example.myfinances2020.repository.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loans")
data class Loan(
    @PrimaryKey(autoGenerate = true)
    val _id: Long = 0L,

    @ColumnInfo(name = "loan_day")
    val day: Int = 0,

    @ColumnInfo(name = "loan_month")
    val month: Int = 0,

    @ColumnInfo(name = "loan_year")
    val year: Int = 0,

    @ColumnInfo(name = "is_lender")
    val isLender: Boolean = true,

    @ColumnInfo(name = "loan_amount")
    val amount: Double = 0.0,

    @ColumnInfo(name = "lonee")
    val loanee: String,

    @ColumnInfo(name = "is_paid")
    val isPayed: Boolean = false
)

fun Loan.formatDate() = "${this.day}/${this.month}/${this.year}"