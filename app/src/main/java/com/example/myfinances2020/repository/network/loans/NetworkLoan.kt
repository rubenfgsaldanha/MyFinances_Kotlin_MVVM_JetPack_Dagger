package com.example.myfinances2020.repository.network.loans

import com.example.myfinances2020.repository.database.entities.Loan
import com.google.gson.annotations.SerializedName

class NetworkLoan (
    val _id: Long,
    val day: Int,
    val month: Int,
    val year: Int,
    @SerializedName("lender") val isLender: Boolean,
    val amount: Double,
    val thirdParty: String,
    @SerializedName("paid") val isPaid: Boolean
)

fun List<NetworkLoan>.asDatabaseModel() =
    this.map { loan ->
        Loan(
            _id = loan._id,
            day = loan.day,
            month = loan.month,
            year = loan.year,
            isLender = loan.isLender,
            amount = loan.amount,
            thirdParty = loan.thirdParty,
            isPayed = loan.isPaid
        )
    }.toTypedArray()