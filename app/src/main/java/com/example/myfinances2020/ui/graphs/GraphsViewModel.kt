package com.example.myfinances2020.ui.graphs

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfinances2020.repository.TransactionsRepository
import com.example.myfinances2020.repository.network.categories.CategoryRepository
import com.example.myfinances2020.utils.SHOW_PERCENTAGES
import com.example.myfinances2020.utils.formatDateWithoutDay
import com.example.myfinances2020.utils.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lecho.lib.hellocharts.model.SliceValue
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.roundToInt

class GraphsViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val transactionsRepository: TransactionsRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var transactions = transactionsRepository.transactions

    private var currentMonth = -1
    private var currentYear = -1
    var valueTotalAmount: String = ""

    private val df2 = DecimalFormat(".##")

    private val _nextMonthBtnClicked = MutableLiveData<Boolean>()
    val nextMonthBtnClicked: LiveData<Boolean> get() = _nextMonthBtnClicked

    private val _previousMonthBtnClicked = MutableLiveData<Boolean>()
    val previousMonthBtnClicked: LiveData<Boolean> get() = _previousMonthBtnClicked

    private val _pieDataList = MutableLiveData<List<SliceValue>?>()
    val pieDataList: LiveData<List<SliceValue>?> get() = _pieDataList

    init {
        val c = getCurrentDate()
        currentMonth = c.get(Calendar.MONTH)
        currentYear = c.get(Calendar.YEAR)
    }

    fun updatePreviousMonth(): String {
        if (currentMonth > 0) {
            currentMonth--
        } else {
            currentMonth = 11
            currentYear--
        }

        getCurrentMonthTransactions()

        return formatDateWithoutDay(currentMonth, currentYear)
    }

    fun updateNextMonth(): String {
        if (currentMonth < 11) {
            currentMonth++
        } else {
            currentMonth = 0
            currentYear++
        }

        getCurrentMonthTransactions()

        return formatDateWithoutDay(currentMonth, currentYear)
    }

    private fun getCurrentMonthTransactions() {
        transactionsRepository.getCurrentMonthTransactions(currentMonth, currentYear)
    }

    fun pieChartLogic() {
        if (transactions.value.isNullOrEmpty()) {
            _pieDataList.value = null
        } else {
            var totalAmount = 0.0
            var totalMoney = 0.0

            val hashMap = HashMap<String, Double>()

            for (transaction in transactions.value!!) {
                totalAmount += transaction.amount
                totalMoney += abs(transaction.amount)
                // if there's already a transaction of a certain category,
                // we calculate the sum of the values of those transactions
                // and update the value in the hashmap
                if (hashMap.containsKey(transaction.category)) {
                    val oldAmount = hashMap[transaction.category]!!
                    val newAmount = oldAmount + transaction.amount
                    hashMap[transaction.category] = newAmount
                } else {
                    hashMap[transaction.category] = transaction.amount
                }
            }

            // now we create a list with the transaction to present the pie chart
            val pieData: MutableList<SliceValue> = ArrayList()
            uiScope.launch {
                for ((key, value1) in hashMap) {
                    val value = value1.roundToInt()
                    val color = categoryRepository.getCategoryByLabel(key).color

                    // calculate percentage and create subtitle if the user wishes
                    var label = ""
                    val showPercentage = sharedPreferences.getBoolean(SHOW_PERCENTAGES, false)
                    if (showPercentage) {
                        val percentage = abs(value1) / totalMoney * 100
                        val percentageString = df2.format(percentage)
                        label = "$percentageString%"
                    }
                    // here we add a value, color and a label
                    pieData.add(SliceValue(value.toFloat(), color).setLabel(label))
                }

                valueTotalAmount = df2.format(totalAmount)
                _pieDataList.value = pieData
            }
        }
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}