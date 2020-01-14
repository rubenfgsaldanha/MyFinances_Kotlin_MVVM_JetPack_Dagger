package com.example.myfinances2020.ui.loans.addLoan

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.myfinances2020.R
import com.example.myfinances2020.databinding.FragmentAddLoanBinding
import com.example.myfinances2020.utils.ViewModelProviderFactory
import com.example.myfinances2020.utils.formatBtnDate
import com.example.myfinances2020.utils.getCurrentDate
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject

class AddLoanFragment : DaggerFragment() {

    private lateinit var viewModel: AddLoanViewModel
    private lateinit var binding: FragmentAddLoanBinding

    @Inject lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddLoanBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.add_loan)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, providerFactory).get(AddLoanViewModel::class.java)
        binding.viewModel = viewModel

        setDate()
        setupObservers()
    }

    private fun setDate() {
        val c = getCurrentDate()
        binding.btnLoanDate.text = formatBtnDate(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR))
    }

    private fun setupObservers() {
        viewModel.pickDate.observe(this, Observer { pick ->
            if (pick) {
                pickDate()
                viewModel.onDatePicked()
            }
        })

        viewModel.navToLoansFragment.observe(this, Observer { navigate ->
            if (navigate) {
                createLoan()
                findNavController().navigate(AddLoanFragmentDirections.actionAddLoanFragmentToLoansFragment())
                viewModel.onReturnedToLoansFragment()
            }
        })
    }

    private fun pickDate() {
        val c = getCurrentDate()

        val datePicker = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, chosenYear, chosenMonth, chosenDay ->
            binding.btnLoanDate.text = formatBtnDate(chosenDay, chosenMonth, chosenYear)
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))

        datePicker.show()
    }

    private fun createLoan() {
        val date = binding.btnLoanDate.text.toString()
        val amount = binding.addLoanAmount.text.toString().toDouble()
        val thirdParty = binding.thirdP.text.toString()

        viewModel.insertLoan(date, amount, thirdParty)
    }
}