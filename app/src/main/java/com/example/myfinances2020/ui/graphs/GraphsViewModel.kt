package com.example.myfinances2020.ui.graphs

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfinances2020.repository.TransactionsRepository
import com.example.myfinances2020.utils.formatDateWithoutDay
import com.example.myfinances2020.utils.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.*
import javax.inject.Inject

class GraphsViewModel @Inject constructor(private val sharedPreferences: SharedPreferences,
                                          private val transactionsRepository: TransactionsRepository) : ViewModel(){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var currentMonth = -1
    private var currentYear = -1

    private val _nextMonthBtnClicked = MutableLiveData<Boolean>()
    val nextMonthBtnClicked: LiveData<Boolean> get() = _nextMonthBtnClicked

    private val _previousMonthBtnClicked = MutableLiveData<Boolean>()
    val previousMonthBtnClicked: LiveData<Boolean> get() = _previousMonthBtnClicked


    init {
        val c = getCurrentDate()
        currentMonth = c.get(Calendar.MONTH)
        currentYear = c.get(Calendar.YEAR)

        //
    }

    fun updatePreviousMonth(): String{
        if(currentMonth > 0){
            currentMonth--
        }
        else{
            currentMonth = 11
            currentYear--
        }

        //

        return formatDateWithoutDay(currentMonth, currentYear)
    }

    fun updateNextMonth(): String{
        if(currentMonth < 11){
            currentMonth++
        }
        else{
            currentMonth = 0
            currentYear++
        }

        //

        return formatDateWithoutDay(currentMonth, currentYear)
    }

    fun onNextMonthBtnClicked(){
        _nextMonthBtnClicked.value = true
    }

    fun onNextMonthBtnClickFinished(){
        _nextMonthBtnClicked.value = false
    }

    fun onPreviousMonthBtnClicked(){
        _previousMonthBtnClicked.value = true
    }

    fun onPreviousMonthBtnClickFinished(){
        _previousMonthBtnClicked.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}