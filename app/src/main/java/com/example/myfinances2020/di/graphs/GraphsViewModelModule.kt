package com.example.myfinances2020.di.graphs

import androidx.lifecycle.ViewModel
import com.example.myfinances2020.di.ViewModelKey
import com.example.myfinances2020.ui.graphs.GraphsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class GraphsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(GraphsViewModel::class)
    abstract fun bindGraphsViewModule(viewModel: GraphsViewModel): ViewModel
}