package com.example.myfinances2020.ui.loans.editLoan

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditTransactionViewModelFactory(private val transactionId: Long, private val application: Application) : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditLoanViewModel::class.java)) {
            return EditLoanViewModel(transactionId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}