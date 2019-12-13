package com.example.myfinances2020.repository.network.transactions

import com.example.myfinances2020.repository.network.Result
import com.example.myfinances2020.utils.safeNetworkCall
import java.io.IOException
import javax.inject.Inject

class TransactionDataSource @Inject constructor(private val transactionService: TransactionService){
    suspend fun getTransactions() = safeNetworkCall(networkCall = { requestGetTransactions() }, errorMessage = "Error getting transactions")

    private suspend fun requestGetTransactions() : Result<List<NetworkTransaction>> {
        val response = transactionService.getAllTransactions()

        if(response.isSuccessful){
            val body = response.body()
            if (body != null){
                return Result.Success(body)
            }
        }
        return Result.Error(IOException("Error getting transactions ${response.code()} ${response.message()}"))
    }
}