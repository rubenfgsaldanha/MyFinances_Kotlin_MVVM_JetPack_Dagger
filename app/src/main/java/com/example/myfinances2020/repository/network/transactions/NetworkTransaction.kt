package com.example.myfinances2020.repository.network.transactions

import com.example.myfinances2020.repository.database.entities.Transaction
import com.google.gson.annotations.SerializedName

data class NetworkTransaction(
    val _id: Long,
    val day: Int,
    val month: Int,
    val year: Int,
    val category: String,
    val comment: String?,
    val amount: Double,
    @SerializedName("expense") val isExpense: Boolean
)

fun List<NetworkTransaction>.asDatabaseModel() =
    this.map { transaction ->
        Transaction(
            _id = transaction._id,
            day = transaction.day,
            month = transaction.month,
            year = transaction.year,
            category = transaction.category,
            comment = transaction.comment,
            amount = transaction.amount,
            isExpense = transaction.isExpense
        )
    }.toTypedArray()
