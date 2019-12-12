package com.example.myfinances2020.di.transactions

import com.example.myfinances2020.data.network.RestApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class TransactionsModule {

    @Provides
    fun provideRestApi(retrofit: Retrofit) : RestApi = retrofit.create(RestApi::class.java)
}