package com.example.myfinances2020.di.transactions

import androidx.lifecycle.ViewModel
import com.example.myfinances2020.di.ViewModelKey
import com.example.myfinances2020.ui.transactions.TransactionsViewModel
import com.example.myfinances2020.ui.transactions.addTransaction.AddTransactionViewModel
import com.example.myfinances2020.ui.transactions.editTransaction.EditTransactionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TransactionsViewModelsModule{

    @Binds
    @IntoMap
    @ViewModelKey(TransactionsViewModel::class)
    abstract fun bindTransactionsViewModel(viewModel: TransactionsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddTransactionViewModel::class)
    abstract fun bindAddTransactionViewModel(viewModel: AddTransactionViewModel) : ViewModel

    /*@Binds
    @IntoMap
    @ViewModelKey(EditTransactionViewModel::class)
    abstract fun bindEditTransactionViewModel(viewModel: EditTransactionViewModel) : ViewModel*/
}