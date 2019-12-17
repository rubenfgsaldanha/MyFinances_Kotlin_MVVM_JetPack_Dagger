package com.example.myfinances2020.di.loans

import com.example.myfinances2020.ui.loans.addLoan.AddLoanFragment
import com.example.myfinances2020.ui.loans.listLoans.ListLoansFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoansFragmentsBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeListLoansFragment() : ListLoansFragment

    @ContributesAndroidInjector
    abstract fun contributeAddLoanFragment() : AddLoanFragment
}