package com.example.myfinances2020.ui.transactions.editTransaction

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myfinances2020.R
import com.example.myfinances2020.databinding.FragmentEditTransactionBinding
import com.example.myfinances2020.utils.formatBtnDate
import com.example.myfinances2020.utils.getCurrentDate
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

class EditTransactionFragment : Fragment() {

    private lateinit var viewModel: EditTransactionViewModel
    private lateinit var binding: FragmentEditTransactionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditTransactionBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.edit_transaction)

        val arguments = EditTransactionFragmentArgs.fromBundle(arguments!!)

        val auxViewModel by viewModel<EditTransactionViewModel> { parametersOf(arguments.transactionId) }
        viewModel = auxViewModel
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.categoryLabels.observe(this, Observer { list ->
            list?.let {
                val categoryAdapter = ArrayAdapter(activity!!.applicationContext, android.R.layout.simple_spinner_item, list)
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerAddCategories.adapter = categoryAdapter

                val index = viewModel.getSelectedIndex() ?: 0
                binding.spinnerAddCategories.setSelection(index)
            }
        })

        viewModel.transaction.observe(this, Observer { transaction ->
            transaction?.let {
                binding.expense.isChecked = transaction.isExpense
                binding.income.isChecked = !transaction.isExpense
            }
        })

        viewModel.pickDate.observe(this, Observer { pickDate ->
            if (pickDate) {
                pickDate()
                viewModel.onDatePicked()
            }
        })

        viewModel.update.observe(this, Observer { update ->
            if (update) {
                updateTransactionValues()
                viewModel.onUpdated()
            }
        })

        viewModel.navToTransactionsFragment.observe(this, Observer { navigate ->
            if (navigate) {
                findNavController().navigate(EditTransactionFragmentDirections.actionEditTransactionFragmentToTransactionsFragment())
                viewModel.onReturnedToTransactionsFragment()
            }
        })
    }

    private fun pickDate() {
        val c = getCurrentDate()

        val datePicker = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, chosenYear, chosenMonth, chosenDay ->
            binding.btnDate.text = formatBtnDate(chosenDay, chosenMonth, chosenYear)
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))

        datePicker.show()
    }

    private fun updateTransactionValues() {
        val date = binding.btnDate.text.toString()
        val amount = binding.amount.text.toString().toDouble()
        val comment = binding.comment.text.toString()
        val category = binding.spinnerAddCategories.selectedItem.toString()
        viewModel.updateTransaction(date, amount, category, comment)
    }
}