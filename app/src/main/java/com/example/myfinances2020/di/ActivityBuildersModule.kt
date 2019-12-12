package com.example.myfinances2020.di

import com.example.myfinances2020.MainActivity
import com.example.myfinances2020.di.scopes.MainScope
import com.example.myfinances2020.di.transactions.TransactionsFragmentsBuildersModule
import com.example.myfinances2020.di.transactions.TransactionsModule
import com.example.myfinances2020.di.transactions.TransactionsViewModelsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @MainScope
    @ContributesAndroidInjector(modules = [TransactionsFragmentsBuildersModule::class, TransactionsViewModelsModule::class,
        TransactionsModule::class])
    abstract fun contributeMainActivity() : MainActivity
}