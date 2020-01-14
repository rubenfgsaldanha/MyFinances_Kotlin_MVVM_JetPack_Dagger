package com.example.myfinances2020.diKoin

import com.example.myfinances2020.repository.TransactionsRepository
import com.example.myfinances2020.repository.database.MyFinancesDatabase
import com.example.myfinances2020.repository.database.daos.TransactionDao
import com.example.myfinances2020.repository.network.transactions.TransactionDataSource
import com.example.myfinances2020.repository.network.transactions.TransactionService
import com.example.myfinances2020.ui.transactions.addTransaction.AddTransactionViewModel
import com.example.myfinances2020.ui.transactions.listTransactions.ListTransactionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val listTransactionsViewModelModule = module {
    viewModel { ListTransactionsViewModel(get(), get()) }
}

val addTransactionsViewModelModule = module {
    viewModel { AddTransactionViewModel(get(), get()) }
}

val transactionsModule = module {
    fun provideTransactionService(retrofit: Retrofit): TransactionService = retrofit.create(TransactionService::class.java)
    fun provideTransactionDao(database: MyFinancesDatabase): TransactionDao = database.transactionDao

    single { provideTransactionService(get()) }
    single { provideTransactionDao(get()) }
}

val transactionRepositoryModule = module {
    single { TransactionsRepository(get(), get()) }
}

val transactionDataSourceModule = module {
    single { TransactionDataSource(get()) }
}





