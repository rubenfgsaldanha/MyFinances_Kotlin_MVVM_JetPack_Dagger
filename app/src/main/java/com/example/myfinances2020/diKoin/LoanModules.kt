package com.example.myfinances2020.diKoin

import com.example.myfinances2020.repository.LoansRepository
import com.example.myfinances2020.repository.database.MyFinancesDatabase
import com.example.myfinances2020.repository.database.daos.LoanDao
import com.example.myfinances2020.repository.network.loans.LoanDataSource
import com.example.myfinances2020.repository.network.loans.LoanService
import com.example.myfinances2020.ui.loans.addLoan.AddLoanViewModel
import com.example.myfinances2020.ui.loans.listLoans.ListLoansViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val listLoansViewModelModule = module {
    viewModel { ListLoansViewModel(get()) }
}

val addLoansViewModelModule = module {
    viewModel { AddLoanViewModel(get()) }
}

val loansModule = module {
    fun provideLoanService(retrofit: Retrofit): LoanService = retrofit.create(LoanService::class.java)
    fun provideLoanDao(database: MyFinancesDatabase): LoanDao = database.loanDao

    single { provideLoanService(get()) }
    single { provideLoanDao(get()) }
}

val loanRepositoryModule = module {
    single { LoansRepository(get(), get()) }
}

val loanDataSourceModule = module {
    single { LoanDataSource(get()) }
}