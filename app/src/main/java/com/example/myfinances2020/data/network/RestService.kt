package com.example.myfinances2020.data.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RestService {
    @GET("transactions/all")
    suspend fun getAllTransactions(): Response<List<NetworkTransaction>>
}