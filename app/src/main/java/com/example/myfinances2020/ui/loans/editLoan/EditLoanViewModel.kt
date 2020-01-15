package com.example.myfinances2020.ui.loans.editLoan

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfinances2020.repository.LoansRepository
import com.example.myfinances2020.repository.database.entities.Loan
import com.example.myfinances2020.repository.database.getDatabase
import com.example.myfinances2020.utils.splitDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EditLoanViewModel(private val loanId: Long = 0L, application: Application) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val database = getDatabase(application)
    private val loanRepository = LoansRepository(database.loanDao, null)

    private val _loan = MediatorLiveData<Loan>()
    val loan: LiveData<Loan> get() = _loan

    private val _navToLoansFragment = MutableLiveData<Boolean>()
    val navToLoansFragment: LiveData<Boolean> get() = _navToLoansFragment

    private val _update = MutableLiveData<Boolean>()
    val update: LiveData<Boolean> get() = _update

    private val _pickDate = MutableLiveData<Boolean>()
    val pickDate: LiveData<Boolean> get() = _pickDate

    private var isLoaner = true
    private var isLoanPaid = false

    init {
        getLoanFromDb()
    }

    fun getLoan() = _loan

    private fun getLoanFromDb() {
        _loan.addSource(loanRepository.getLoanById(loanId), _loan::setValue)
    }

    fun updateLoan(dateString: String, amount: Double, thirdParty: String) {
        val date = splitDate(dateString)
        val l = Loan(_loan.value!!._id, date[0].toInt(), date[1].toInt(), date[2].toInt(), isLoaner, amount, thirdParty, isLoanPaid)

        uiScope.launch { loanRepository.updateLoan(l) }
        onReturnToLoansFragment()
    }

    fun deleteLoan() {
        uiScope.launch { loanRepository.deleteLoanById(_loan.value!!._id) }
        onReturnToLoansFragment()
    }

    fun onPickDate() {
        _pickDate.value = true
    }

    fun onDatePicked() {
        _pickDate.value = false
    }

    fun onLoanerClicked() {
        isLoaner = true
    }

    fun onLoaneeClicked() {
        isLoaner = false
    }

    fun onIsPayedClicked() {
        isLoanPaid = true
    }

    fun onIsNotPayedClicked() {
        isLoanPaid = false
    }

    fun onUpdate() {
        _update.value = true
    }

    fun onUpdated() {
        _update.value = false
    }

    private fun onReturnToLoansFragment() {
        _navToLoansFragment.value = true
    }

    fun onReturnedToLoansFragment() {
        _navToLoansFragment.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}