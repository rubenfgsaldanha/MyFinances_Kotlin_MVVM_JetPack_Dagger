package com.example.myfinances2020.ui.transactions.listTransactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfinances2020.repository.TransactionsRepository
import com.example.myfinances2020.repository.network.categories.CategoryRepository
import com.example.myfinances2020.utils.formatDateWithoutDay
import com.example.myfinances2020.utils.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class ListTransactionsViewModel @Inject constructor(private val transactionsRepository: TransactionsRepository,
                                                    private val categoryRepository: CategoryRepository) : ViewModel(){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var transactions = transactionsRepository.transactions

    private var currentMonth = -1
    private var currentYear = -1

    private val _nextMonthBtnClicked = MutableLiveData<Boolean>()
    val nextMonthBtnClicked: LiveData<Boolean> get() = _nextMonthBtnClicked

    private val _previousMonthBtnClicked = MutableLiveData<Boolean>()
    val previousMonthBtnClicked: LiveData<Boolean> get() = _previousMonthBtnClicked

    private val _navToAddTransaction = MutableLiveData<Boolean>()
    val navToAddTransaction: LiveData<Boolean> get() = _navToAddTransaction

    private val _navToEditTransaction = MutableLiveData<Long>()
    val navToEditTransaction: LiveData<Long> get() = _navToEditTransaction


    init {
        val c = getCurrentDate()
        currentMonth = c.get(Calendar.MONTH)
        currentYear = c.get(Calendar.YEAR)

        refreshCategories()
        refreshTransactions()
    }

    private fun refreshTransactions() {
        uiScope.launch { transactionsRepository.refreshTransactions() }
    }

    private fun refreshCategories(){
        uiScope.launch { categoryRepository.refreshCategories() }
    }

    fun updatePreviousMonth(): String{
        if(currentMonth > 0){
            currentMonth--
        }
        else{
            currentMonth = 11
            currentYear--
        }

        getCurrentMonthTransactions()

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

        getCurrentMonthTransactions()

        return formatDateWithoutDay(currentMonth, currentYear)
    }

    private fun getCurrentMonthTransactions(){
        transactionsRepository.getCurrentMonthTransactions(currentMonth, currentYear)
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

    fun onFabClicked() {
        _navToAddTransaction.value = true
    }

    fun onNavigatedToAddTransaction() {
        _navToAddTransaction.value = false
    }

    fun onTransactionClicked(id: Long){
        _navToEditTransaction.value = id
    }

    fun onNavigatedToEditTransaction(){
        _navToEditTransaction.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}