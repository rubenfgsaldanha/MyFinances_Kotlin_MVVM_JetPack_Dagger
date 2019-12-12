package com.example.myfinances2020.di

import androidx.lifecycle.ViewModelProvider
import com.example.myfinances2020.utils.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(modelProviderFactory: ViewModelProviderFactory) : ViewModelProvider.Factory
}