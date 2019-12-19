package com.example.myfinances2020.ui.loans.editLoan

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
import com.example.myfinances2020.databinding.FragmentEditLoanBinding
import com.example.myfinances2020.utils.formatBtnDate
import com.example.myfinances2020.utils.getCurrentDate
import java.util.*

class EditLoanFragment : Fragment() {

    private lateinit var viewModel: EditLoanViewModel
    private lateinit var binding: FragmentEditLoanBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditLoanBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.edit_transaction)

        val application = requireNotNull(this.activity).application
        val arguments = EditLoanFragmentArgs.fromBundle(arguments!!)
        val viewModelFactory = EditTransactionViewModelFactory(arguments.loanId, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EditLoanViewModel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.loan.observe(this, Observer { loan ->
            loan?.let {
                binding.editlender.isChecked = loan.isLender
                binding.editlendee.isChecked = !loan.isLender
                binding.payed.isChecked = loan.isPayed
                binding.notPayed.isChecked = !loan.isPayed
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
                updateLoanValues()
                viewModel.onUpdated()
            }
        })

        viewModel.navToLoansFragment.observe(this, Observer { navigate ->
            if (navigate) {
                findNavController().navigate(EditLoanFragmentDirections.actionEditLoanFragmentToLoansFragment())
                viewModel.onReturnedToLoansFragment()
            }
        })
    }

    private fun pickDate() {
        val c = getCurrentDate()

        val datePicker = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, chosenYear, chosenMonth, chosenDay ->
            binding.btnEditLoanDate.text = formatBtnDate(chosenDay, chosenMonth, chosenYear)
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))

        datePicker.show()
    }

    private fun updateLoanValues() {
        val date = binding.btnEditLoanDate.text.toString()
        val amount = binding.editLoanAmount.text.toString().toDouble()
        val thirdParty = binding.editthirdP.text.toString()
        viewModel.updateLoan(date, amount, thirdParty)
    }
}