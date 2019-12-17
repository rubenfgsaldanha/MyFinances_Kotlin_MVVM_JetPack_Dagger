package com.example.myfinances2020.repository.network.loans

import retrofit2.Response
import retrofit2.http.GET

interface LoanService {
    @GET("api/loans/all")
    suspend fun getAllLoans() : Response<List<NetworkLoan>>
}