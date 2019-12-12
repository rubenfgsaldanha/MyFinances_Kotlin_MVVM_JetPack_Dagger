package com.example.myfinances2020.di

import com.example.myfinances2020.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity() : MainActivity
}