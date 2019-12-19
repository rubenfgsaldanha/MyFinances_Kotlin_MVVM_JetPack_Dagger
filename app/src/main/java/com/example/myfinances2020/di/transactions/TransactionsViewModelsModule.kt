package com.example.myfinances2020.di.transactions

import androidx.lifecycle.ViewModel
import com.example.myfinances2020.di.ViewModelKey
import com.example.myfinances2020.ui.transactions.listTransactions.ListTransactionsViewModel
import com.example.myfinances2020.ui.transactions.addTransaction.AddTransactionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TransactionsViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListTransactionsViewModel::class)
    abstract fun bindListTransactionsViewModel(viewModel: ListTransactionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddTransactionViewModel::class)
    abstract fun bindAddTransactionViewModel(viewModel: AddTransactionViewModel): ViewModel

    /*@Binds
    @IntoMap
    @ViewModelKey(EditTransactionViewModel::class)
    abstract fun bindEditTransactionViewModel(viewModel: EditTransactionViewModel) : ViewModel*/
}