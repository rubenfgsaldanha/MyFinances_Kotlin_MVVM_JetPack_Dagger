package com.example.myfinances2020.di

import com.example.myfinances2020.MainActivity
import com.example.myfinances2020.di.graphs.GraphsFragmentBuilderModule
import com.example.myfinances2020.di.graphs.GraphsViewModelModule
import com.example.myfinances2020.di.loans.LoansFragmentsBuildersModule
import com.example.myfinances2020.di.loans.LoansModule
import com.example.myfinances2020.di.loans.LoansViewModelsModule
import com.example.myfinances2020.di.scopes.MainScope
import com.example.myfinances2020.di.settings.SettingsFragmentBuilderModule
import com.example.myfinances2020.di.settings.SettingsViewModelModule
import com.example.myfinances2020.di.transactions.TransactionsFragmentsBuildersModule
import com.example.myfinances2020.di.transactions.TransactionsModule
import com.example.myfinances2020.di.transactions.TransactionsViewModelsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @MainScope
    @ContributesAndroidInjector(modules = [TransactionsFragmentsBuildersModule::class, TransactionsViewModelsModule::class,
        TransactionsModule::class, LoansFragmentsBuildersModule::class, LoansViewModelsModule::class, LoansModule::class,
        SettingsFragmentBuilderModule::class, SettingsViewModelModule::class, GraphsFragmentBuilderModule::class,
        GraphsViewModelModule::class])
    abstract fun contributeMainActivity(): MainActivity
}