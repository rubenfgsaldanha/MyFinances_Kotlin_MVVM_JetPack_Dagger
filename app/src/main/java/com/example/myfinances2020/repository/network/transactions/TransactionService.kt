package com.example.myfinances2020.repository.network.transactions

import retrofit2.Response
import retrofit2.http.GET

interface TransactionService {
    @GET("transactions/all")
    suspend fun getAllTransactions(): Response<List<NetworkTransaction>>
}