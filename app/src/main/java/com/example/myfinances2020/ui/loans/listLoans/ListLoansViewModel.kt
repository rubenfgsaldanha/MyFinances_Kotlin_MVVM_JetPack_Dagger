package com.example.myfinances2020.ui.loans.listLoans

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfinances2020.repository.LoansRepository
import com.example.myfinances2020.utils.formatDateWithoutDay
import com.example.myfinances2020.utils.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class ListLoansViewModel @Inject constructor(private val loansRepository: LoansRepository) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var loans = loansRepository.loans

    private var currentMonth = -1
    private var currentYear = -1

    private val _nextMonthBtnClicked = MutableLiveData<Boolean>()
    val nextMonthBtnClicked: LiveData<Boolean> get() = _nextMonthBtnClicked

    private val _previousMonthBtnClicked = MutableLiveData<Boolean>()
    val previousMonthBtnClicked: LiveData<Boolean> get() = _previousMonthBtnClicked

    private val _navToAddLoan = MutableLiveData<Boolean>()
    val navToAddLoan: LiveData<Boolean> get() = _navToAddLoan

    private val _navToEditLoan = MutableLiveData<Long>()
    val navToEditLoan: LiveData<Long> get() = _navToEditLoan

    init {
        val c = getCurrentDate()
        currentMonth = c.get(Calendar.MONTH)
        currentYear = c.get(Calendar.YEAR)

        refreshLoans()
    }

    private fun refreshLoans() = uiScope.launch { loansRepository.refreshLoans() }

    fun updatePreviousMonth(): String {
        if (currentMonth > 0) {
            currentMonth--
        } else {
            currentMonth = 11
            currentYear--
        }

        getCurrentMonthLoans()

        return formatDateWithoutDay(currentMonth, currentYear)
    }

    fun updateNextMonth(): String {
        if (currentMonth < 11) {
            currentMonth++
        } else {
            currentMonth = 0
            currentYear++
        }

        getCurrentMonthLoans()

        return formatDateWithoutDay(currentMonth, currentYear)
    }

    private fun getCurrentMonthLoans() {
        loansRepository.getCurrentMonthLoans(currentMonth, currentYear)
    }

    fun onNextMonthBtnClicked() {
        _nextMonthBtnClicked.value = true
    }

    fun onNextMonthBtnClickFinished() {
        _nextMonthBtnClicked.value = false
    }

    fun onPreviousMonthBtnClicked() {
        _previousMonthBtnClicked.value = true
    }

    fun onPreviousMonthBtnClickFinished() {
        _previousMonthBtnClicked.value = false
    }

    fun onFabClicked() {
        _navToAddLoan.value = true
    }

    fun onNavigatedToAddLoan() {
        _navToAddLoan.value = false
    }

    fun onLoanClicked(id: Long) {
        _navToEditLoan.value = id
    }

    fun onNavigatedToEditLoan() {
        _navToEditLoan.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}