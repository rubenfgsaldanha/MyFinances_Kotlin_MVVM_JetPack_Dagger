package com.example.myfinances2020.data.network

import android.os.Parcelable
import com.example.myfinances2020.data.database.entities.Transaction
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NetworkTransaction(
    val _id: Long,
    val day: Int,
    val month: Int,
    val year: Int,
    val category: String,
    val comment: String?,
    val amount: Double,
    @Json(name = "expense") val isExpense: Boolean
) : Parcelable

fun List<NetworkTransaction>.asDatabaseModel(): Array<Transaction>{
    return this.map { transaction ->
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
}
