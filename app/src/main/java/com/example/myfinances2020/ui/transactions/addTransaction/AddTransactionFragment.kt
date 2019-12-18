package com.example.myfinances2020.ui.transactions.addTransaction

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.myfinances2020.R
import com.example.myfinances2020.databinding.FragmentAddTransactionBinding
import com.example.myfinances2020.utils.ViewModelProviderFactory
import com.example.myfinances2020.utils.formatBtnDate
import com.example.myfinances2020.utils.getCurrentDate
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject

class AddTransactionFragment : DaggerFragment(){

    private lateinit var viewModel: AddTransactionViewModel
    private lateinit var binding: FragmentAddTransactionBinding

    @Inject lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddTransactionBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.add_transaction)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, providerFactory).get(AddTransactionViewModel::class.java)
        binding.viewModel = viewModel

        setDate()
        setupObservers()
    }

    private fun setDate(){
        val c = getCurrentDate()
        binding.btnDate.text = formatBtnDate(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR))
    }

    private fun setupObservers() {
        viewModel.categoryLabels.observe(this, Observer { list ->
            list?.let {
                val categoryAdapter = ArrayAdapter(activity!!.applicationContext, android.R.layout.simple_spinner_item, list)
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerAddCategories.adapter = categoryAdapter
            }
        })

        viewModel.pickDate.observe(this, Observer { pick ->
            if(pick){
                pickDate()
                viewModel.onDatePicked()
            }
        })

        viewModel.navToTransactionsFragment.observe(this, Observer { navigate ->
            if(navigate){
                createTransaction()
                findNavController().navigate(AddTransactionFragmentDirections.actionAddTransactionFragmentToTransactionsFragment())
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
        val category = binding.spinnerAddCategories.selectedItem.toString()

        viewModel.insertTransaction(date, category, comment, amount)
    }
}