package com.example.myfinances2020.di.transactions

import com.example.myfinances2020.di.scopes.MainScope
import com.example.myfinances2020.repository.database.MyFinancesDatabase
import com.example.myfinances2020.repository.database.daos.TransactionDao
import com.example.myfinances2020.repository.network.transactions.TransactionService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class TransactionsModule {

    @MainScope
    @Provides
    fun provideTransactionService(retrofit: Retrofit) : TransactionService = retrofit.create(TransactionService::class.java)

    @MainScope
    @Provides
    fun provideTransactionDao(database: MyFinancesDatabase) : TransactionDao = database.transactionDao
}