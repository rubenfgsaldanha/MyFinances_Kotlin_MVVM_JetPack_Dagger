package com.example.myfinances2020.ui.transactions.editTransaction

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditTransactionViewModelFactory(private val transactionId: Long, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditTransactionViewModel::class.java)) {
            return EditTransactionViewModel(transactionId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}