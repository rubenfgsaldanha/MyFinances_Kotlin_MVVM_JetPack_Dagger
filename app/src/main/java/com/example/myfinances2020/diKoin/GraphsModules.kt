package com.example.myfinances2020.diKoin

import com.example.myfinances2020.ui.graphs.GraphsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val graphsViewModelModule = module {
    viewModel { GraphsViewModel(get(), get(), get()) }
}