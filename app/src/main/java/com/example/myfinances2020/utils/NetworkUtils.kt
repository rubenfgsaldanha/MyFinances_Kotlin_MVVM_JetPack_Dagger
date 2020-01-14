package com.example.myfinances2020.utils

import com.example.myfinances2020.repository.network.Result
import java.io.IOException
import java.lang.Exception

suspend fun <T : Any> safeNetworkCall(networkCall: suspend () -> Result<T>, errorMessage: String): Result<T> {
    return try {
        networkCall()
    } catch (e: Exception) {
        Result.Error(IOException(errorMessage, e))
    }
}