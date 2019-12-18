package com.example.myfinances2020.di.graphs

import com.example.myfinances2020.ui.graphs.GraphsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GraphsFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeGraphsFragment() : GraphsFragment
}