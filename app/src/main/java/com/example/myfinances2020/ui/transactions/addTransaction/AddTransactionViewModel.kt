package com.example.myfinances2020.ui.transactions.addTransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfinances2020.repository.database.entities.Transaction
import com.example.myfinances2020.repository.TransactionsRepository
import com.example.myfinances2020.repository.network.categories.CategoryRepository
import com.example.myfinances2020.utils.splitDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTransactionViewModel @Inject constructor(private val transactionsRepository: TransactionsRepository,
                                                  categoryRepository: CategoryRepository) : ViewModel(){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var categoryLabels = categoryRepository.categoryLabels

    private val _navToTransactionsFragment = MutableLiveData<Boolean>()
    val navToTransactionsFragment: LiveData<Boolean> get() = _navToTransactionsFragment

    private val _pickDate = MutableLiveData<Boolean>()
    val pickDate: LiveData<Boolean> get() = _pickDate

    private var isExpense = true


    fun onPickDate(){
        _pickDate.value = true
    }

    fun onDatePicked(){
        _pickDate.value = false
    }

    fun onReturnToTransactionsFragment() {
        _navToTransactionsFragment.value = true
    }

    fun onReturnedToTransactionsFragment() {
        _navToTransactionsFragment.value = false
    }

    fun onExpenseClicked(){
        isExpense = true
    }

    fun onIncomeClicked(){
        isExpense = false
    }

    fun insertTransaction(dateString: String, category: String, comment: String, amount: Double){
        val date = splitDate(dateString)

        val t = Transaction(0, date[0].toInt(), date[1].toInt(), date[2].toInt(),
            category, comment, amount, isExpense)

        uiScope.launch {
            transactionsRepository.insertTransaction(t)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}