package com.example.myfinances2020.di.transactions

import com.example.myfinances2020.ui.transactions.TransactionsFragment
import com.example.myfinances2020.ui.transactions.addTransaction.AddTransactionFragment
import com.example.myfinances2020.ui.transactions.editTransaction.EditTransactionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TransactionsFragmentsBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeTransactionsFragment() : TransactionsFragment

    @ContributesAndroidInjector
    abstract fun contributeAddTransactionFragment() : AddTransactionFragment

    @ContributesAndroidInjector
    abstract fun contributeEditTransactionFragment() : EditTransactionFragment
}