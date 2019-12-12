package com.example.myfinances2020.repository.network

import retrofit2.Response
import retrofit2.http.GET

interface RestService {
    @GET("transactions/all")
    suspend fun getAllTransactions(): Response<List<NetworkTransaction>>
}