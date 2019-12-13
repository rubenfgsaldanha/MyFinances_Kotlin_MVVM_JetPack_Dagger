package com.example.myfinances2020.di.loans

import androidx.lifecycle.ViewModel
import com.example.myfinances2020.di.ViewModelKey
import com.example.myfinances2020.ui.loans.listLoans.ListLoansViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LoansViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListLoansViewModel::class)
    abstract fun bindListLoansViewModule(viewModel: ListLoansViewModel) : ViewModel
}