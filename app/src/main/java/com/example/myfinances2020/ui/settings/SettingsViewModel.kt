package com.example.myfinances2020.ui.settings

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfinances2020.utils.SHOW_PERCENTAGES
import com.example.myfinances2020.utils.SHOW_SUBTITLES
import javax.inject.Inject

class SettingsViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) : ViewModel(){

    private val _showPercentage = MutableLiveData<Boolean>()
    val showPercentage: LiveData<Boolean> get() = _showPercentage

    private val _showSubtitles = MutableLiveData<Boolean>()
    val showSubtitles: LiveData<Boolean> get() = _showSubtitles

    init {
        _showPercentage.value = sharedPreferences.getBoolean(SHOW_PERCENTAGES, false)
        _showSubtitles.value = sharedPreferences.getBoolean(SHOW_SUBTITLES, false)
    }

    fun onShowPercentageClicked(){
        _showPercentage.value = !_showPercentage.value!!
    }

    fun onShowSubtitlesClicked(){
        _showSubtitles.value = !_showSubtitles.value!!
    }

    fun onShowPercentagesClickFinished(){
        sharedPreferences.edit().putBoolean(SHOW_PERCENTAGES, _showPercentage.value!!).apply()
    }

    fun onShowSubtitlesClickFinished(){
        sharedPreferences.edit().putBoolean(SHOW_SUBTITLES, _showSubtitles.value!!).apply()
    }
}