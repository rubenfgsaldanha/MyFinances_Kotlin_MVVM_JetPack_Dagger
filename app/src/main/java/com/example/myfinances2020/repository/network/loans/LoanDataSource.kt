package com.example.myfinances2020.repository.network.loans

import com.example.myfinances2020.repository.network.Result
import com.example.myfinances2020.utils.safeNetworkCall
import java.io.IOException
import javax.inject.Inject

class LoanDataSource @Inject constructor(private val loanService: LoanService) {
    suspend fun getLoans() = safeNetworkCall(networkCall = { requestGetLoans() }, errorMessage = "Error getting loans")

    private suspend fun requestGetLoans(): Result<List<NetworkLoan>> {
        val response = loanService.getAllLoans()

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }
        return Result.Error(IOException("Error getting loans ${response.code()} ${response.message()}"))
    }
}