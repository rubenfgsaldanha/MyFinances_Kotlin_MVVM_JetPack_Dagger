package com.example.myfinances2020.di.settings

import com.example.myfinances2020.ui.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingsFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment
}