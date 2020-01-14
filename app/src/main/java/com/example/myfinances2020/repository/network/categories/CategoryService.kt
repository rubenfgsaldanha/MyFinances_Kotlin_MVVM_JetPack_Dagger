package com.example.myfinances2020.repository.network.categories

import retrofit2.Response
import retrofit2.http.GET

interface CategoryService {
    @GET("api/categories/all")
    suspend fun getAllCategories(): Response<List<NetworkCategory>>
}