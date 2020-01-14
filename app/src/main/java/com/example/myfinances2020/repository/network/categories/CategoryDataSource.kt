package com.example.myfinances2020.repository.network.categories

import com.example.myfinances2020.repository.network.Result
import com.example.myfinances2020.utils.safeNetworkCall
import java.io.IOException
import javax.inject.Inject

class CategoryDataSource @Inject constructor(private val categoryService: CategoryService) {

    suspend fun getCategories() = safeNetworkCall(networkCall = { requestGetCategories() }, errorMessage = "Error getting categories")

    private suspend fun requestGetCategories(): Result<List<NetworkCategory>> {
        val response = categoryService.getAllCategories()

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }
        return Result.Error(IOException("Error getting categories ${response.code()} - ${response.message()}"))
    }
}