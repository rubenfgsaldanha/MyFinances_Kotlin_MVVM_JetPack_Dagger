package com.example.myfinances2020.ui.transactions.editTransaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.myfinances2020.repository.TransactionsRepository
import com.example.myfinances2020.repository.database.entities.Transaction
import com.example.myfinances2020.repository.database.getDatabase
import com.example.myfinances2020.repository.network.categories.CategoryRepository
import com.example.myfinances2020.utils.splitDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EditTransactionViewModel(private val transactionId: Long = 0L, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val database = getDatabase(application)
    private val transactionsRepository = TransactionsRepository(database.transactionDao, null)
    private val categoryRepository = CategoryRepository(database.categoryDao, null)

    var categoryLabels = categoryRepository.categoryLabels

    private val _transaction = MediatorLiveData<Transaction>()
    val transaction: LiveData<Transaction> get() = _transaction

    private val _navToTransactionsFragment = MutableLiveData<Boolean>()
    val navToTransactionsFragment: LiveData<Boolean> get() = _navToTransactionsFragment

    private val _update = MutableLiveData<Boolean>()
    val update: LiveData<Boolean> get() = _update

    private val _pickDate = MutableLiveData<Boolean>()
    val pickDate: LiveData<Boolean> get() = _pickDate

    private var isExpense = true

    init {
        getTransactionFromDb()
    }

    fun getTransaction() = _transaction

    private fun getTransactionFromDb() {
        _transaction.addSource(transactionsRepository.getTransactionById(transactionId), _transaction::setValue)
    }

    fun updateTransaction(dateString: String, amount: Double, category: String, comment: String) {
        val date = splitDate(dateString)
        val t = Transaction(_transaction.value!!._id, date[0].toInt(), date[1].toInt(), date[2].toInt(),
            category, comment, amount, isExpense)
        uiScope.launch {
            transactionsRepository.updateTransaction(t)
        }
        onReturnToTransactionsFragment()
    }

    fun deleteTransaction() {
        uiScope.launch {
            transactionsRepository.deleteTransactionById(_transaction.value!!._id)
        }
        onReturnToTransactionsFragment()
    }

    fun getSelectedIndex(): Int? {
        val labels = categoryLabels.value
        labels?.let {
            for (i in labels.indices) {
                if (labels[i] == transaction.value?.category) {
                    return i
                }
            }
        }
        return null
    }

    fun onPickDate() {
        _pickDate.value = true
    }

    fun onDatePicked() {
        _pickDate.value = false
    }

    fun onExpenseClicked() {
        isExpense = true
    }

    fun onIncomeClicked() {
        isExpense = false
    }

    fun onUpdate() {
        _update.value = true
    }

    fun onUpdated() {
        _update.value = false
    }

    private fun onReturnToTransactionsFragment() {
        _navToTransactionsFragment.value = true
    }

    fun onReturnedToTransactionsFragment() {
        _navToTransactionsFragment.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}