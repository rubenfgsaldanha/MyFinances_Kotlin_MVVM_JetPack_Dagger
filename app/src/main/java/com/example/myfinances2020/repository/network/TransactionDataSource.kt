package com.example.myfinances2020.repository.network

import com.example.myfinances2020.utils.safeNetworkCall
import java.io.IOException
import javax.inject.Inject

class TransactionDataSource @Inject constructor(private val restService: RestService){
    suspend fun getTransactions() = safeNetworkCall(networkCall = { requestGetTransactions() }, errorMessage = "Error getting transactions")

    private suspend fun requestGetTransactions() : Result<List<NetworkTransaction>>{
        val response = restService.getAllTransactions()

        if(response.isSuccessful){
            val body = response.body()
            if (body != null){
                return Result.Success(body)
            }
        }
        return Result.Error(IOException("Error getting transactions ${response.code()} ${response.message()}"))
    }
}