package com.example.myfinances2020.di.loans

import com.example.myfinances2020.di.scopes.MainScope
import com.example.myfinances2020.repository.database.MyFinancesDatabase
import com.example.myfinances2020.repository.database.daos.LoanDao
import com.example.myfinances2020.repository.network.loans.LoanService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class LoansModule {

    @MainScope
    @Provides
    fun provideLoanService(retrofit: Retrofit) : LoanService = retrofit.create(LoanService::class.java)

    @MainScope
    @Provides
    fun provideLoanDao(database: MyFinancesDatabase) : LoanDao = database.loanDao
}