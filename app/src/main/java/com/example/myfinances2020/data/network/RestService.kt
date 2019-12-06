package com.example.myfinances2020.data.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(RestService.ENDPOINT)
    .build()

interface RestService {
    @GET("transactions/all")
    suspend fun getAllTransactions(): Response<List<NetworkTransaction>>

    companion object{
        const val ENDPOINT = "http://192.168.1.235:8080/"
    }
}

object RestApi{
    val restService : RestService by lazy { retrofit.create(RestService::class.java) }
}