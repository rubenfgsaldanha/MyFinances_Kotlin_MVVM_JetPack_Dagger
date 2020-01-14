package com.example.myfinances2020.di.settings

import androidx.lifecycle.ViewModel
import com.example.myfinances2020.di.ViewModelKey
import com.example.myfinances2020.ui.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SettingsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModule(viewModel: SettingsViewModel): ViewModel
}