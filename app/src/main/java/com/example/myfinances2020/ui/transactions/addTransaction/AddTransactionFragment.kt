package com.example.myfinances2020.ui.transactions.addTransaction

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.myfinances2020.R
import com.example.myfinances2020.databinding.FragmentAddTransactionBinding
import com.example.myfinances2020.utils.formatBtnDate
import com.example.myfinances2020.utils.getCurrentDate
import java.util.*

class AddTransactionFragment : Fragment(){

    private lateinit var viewModel: AddTransactionViewModel
    private lateinit var binding: FragmentAddTransactionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddTransactionBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.add_transaction)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = AddTransactionViewModelFactory(application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddTransactionViewModel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        setDate()

        setupObservers()

        return binding.root
    }

    private fun setDate(){
        val c = getCurrentDate()
        binding.btnDate.text = formatBtnDate(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR))
    }

    private fun setupObservers() {
        viewModel.pickDate.observe(this, Observer { pick ->
            if(pick){
                pickDate()
                viewModel.onDatePicked()
            }
        })

        viewModel.navToTransactionsFragment.observe(this, Observer { navigate ->
            if(navigate){
                createTransaction()
                this.findNavController().navigate(AddTransactionFragmentDirections.actionAddTransactionFragmentToTransactionsFragment())
                viewModel.onReturnedToTransactionsFragment()
            }
        })
    }

    private fun pickDate(){
        val c = getCurrentDate()

        val datePicker = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener{ _, chosenYear, chosenMonth, chosenDay ->
            binding.btnDate.text = formatBtnDate(chosenDay, chosenMonth, chosenYear)
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))

        datePicker.show()
    }

    private fun createTransaction(){
        val date = binding.btnDate.text.toString()
        val comment = binding.comment.text.toString()
        val amount = binding.amount.text.toString().toDouble()

        viewModel.insertTransaction(date, comment, amount)
    }
}