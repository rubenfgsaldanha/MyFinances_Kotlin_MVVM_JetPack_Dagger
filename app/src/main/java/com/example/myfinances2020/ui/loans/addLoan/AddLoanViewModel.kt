package com.example.myfinances2020.ui.loans.addLoan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfinances2020.repository.LoansRepository
import com.example.myfinances2020.repository.database.entities.Loan
import com.example.myfinances2020.utils.splitDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddLoanViewModel @Inject constructor(private val loansRepository: LoansRepository) : ViewModel(){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navToLoansFragment = MutableLiveData<Boolean>()
    val navToLoansFragment: LiveData<Boolean> get() = _navToLoansFragment

    private val _pickDate = MutableLiveData<Boolean>()
    val pickDate: LiveData<Boolean> get() = _pickDate

    private var isLender = true


    fun onPickDate(){
        _pickDate.value = true
    }

    fun onDatePicked(){
        _pickDate.value = false
    }

    fun onReturnToLoansFragment() {
        _navToLoansFragment.value = true
    }

    fun onReturnedToLoansFragment() {
        _navToLoansFragment.value = false
    }

    fun onIsLenderClicked(){
        isLender = true
    }

    fun onIsLendeeClicked(){
        isLender = false
    }

    fun insertLoan(dateString: String, amount: Double, thirdParty: String){
        val date = splitDate(dateString)

        val l = Loan(0, date[0].toInt(), date[1].toInt(), date[2].toInt(),
            isLender, amount, thirdParty)

        uiScope.launch {
            loansRepository.insertLoan(l)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}