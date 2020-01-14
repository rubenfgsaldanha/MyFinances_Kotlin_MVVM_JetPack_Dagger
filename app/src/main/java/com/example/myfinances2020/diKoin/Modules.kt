package com.example.myfinances2020.diKoin

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.myfinances2020.repository.CategoryRepository
import com.example.myfinances2020.repository.LoansRepository
import com.example.myfinances2020.repository.TransactionsRepository
import com.example.myfinances2020.repository.database.MyFinancesDatabase
import com.example.myfinances2020.repository.database.daos.CategoryDao
import com.example.myfinances2020.repository.database.daos.LoanDao
import com.example.myfinances2020.repository.database.daos.TransactionDao
import com.example.myfinances2020.repository.database.getDatabase
import com.example.myfinances2020.repository.network.categories.CategoryDataSource
import com.example.myfinances2020.repository.network.categories.CategoryService
import com.example.myfinances2020.repository.network.loans.LoanDataSource
import com.example.myfinances2020.repository.network.loans.LoanService
import com.example.myfinances2020.repository.network.transactions.TransactionDataSource
import com.example.myfinances2020.repository.network.transactions.TransactionService
import com.example.myfinances2020.ui.graphs.GraphsViewModel
import com.example.myfinances2020.ui.loans.addLoan.AddLoanViewModel
import com.example.myfinances2020.ui.loans.listLoans.ListLoansViewModel
import com.example.myfinances2020.ui.settings.SettingsViewModel
import com.example.myfinances2020.ui.transactions.addTransaction.AddTransactionViewModel
import com.example.myfinances2020.ui.transactions.listTransactions.ListTransactionsViewModel
import com.example.myfinances2020.utils.ENDPOINT
import com.example.myfinances2020.utils.SHARED_PREFS
import com.google.gson.Gson
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Module that contains all viewModels
 */
val viewModelsModule = module {
    viewModel { ListLoansViewModel(get()) }
    viewModel { AddLoanViewModel(get()) }
    viewModel { ListTransactionsViewModel(get(), get()) }
    viewModel { AddTransactionViewModel(get(), get()) }
    viewModel { GraphsViewModel(get(), get(), get()) }
    viewModel { SettingsViewModel(get()) }
}


/**
 * Module that contains transaction related dependencies
 */
val transactionsModule = module {
    fun provideTransactionService(retrofit: Retrofit): TransactionService = retrofit.create(TransactionService::class.java)
    fun provideTransactionDao(database: MyFinancesDatabase): TransactionDao = database.transactionDao

    single { provideTransactionService(get()) }
    single { provideTransactionDao(get()) }

    single { TransactionsRepository(get(), get()) }
    single { TransactionDataSource(get()) }
}


/**
 * Module that contains loan related dependencies
 */
val loansModule = module {
    fun provideLoanService(retrofit: Retrofit): LoanService = retrofit.create(LoanService::class.java)
    fun provideLoanDao(database: MyFinancesDatabase): LoanDao = database.loanDao

    single { provideLoanService(get()) }
    single { provideLoanDao(get()) }

    single { LoansRepository(get(), get()) }
    single { LoanDataSource(get()) }
}


/**
 * Module that contains category related dependencies
 */
val categoriesModule = module {
    fun provideCategoryService(retrofit: Retrofit): CategoryService = retrofit.create(CategoryService::class.java)
    fun provideCategoryDao(database: MyFinancesDatabase): CategoryDao = database.categoryDao

    single { provideCategoryService(get()) }
    single { provideCategoryDao(get()) }

    single { CategoryRepository(get(), get()) }
    single { CategoryDataSource(get()) }
}


/**
 * Module that contains retrofit related dependencies
 */
val retrofitModule = module {
    fun provideGson(): Gson = Gson()

    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    fun provideRetrofitInstance(converterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(converterFactory)
            .build()
    }

    single { provideGson() }
    single { provideGsonConverterFactory(get()) }
    single { provideRetrofitInstance(get()) }
}


/**
 * Module that provides a database instance
 */
val databaseModule = module {
    fun provideDatabaseInstance(application: Application) = getDatabase(application)

    single { provideDatabaseInstance(androidApplication()) }
}


/**
 * Module that provides a shared preferences instance
 */
val sharedPreferencesModule = module {
    fun provideSharedPreferences(application: Application): SharedPreferences = application.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    single { provideSharedPreferences(androidApplication()) }
}