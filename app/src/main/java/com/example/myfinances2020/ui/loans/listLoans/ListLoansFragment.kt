package com.example.myfinances2020.ui.loans.listLoans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.myfinances2020.R
import com.example.myfinances2020.databinding.FragmentListLoansBinding
import com.example.myfinances2020.utils.ViewModelProviderFactory
import com.example.myfinances2020.utils.formatDateWithoutDay
import com.example.myfinances2020.utils.getCurrentDate
import com.example.myfinances2020.utils.setCurrentDate
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject

class ListLoansFragment : DaggerFragment(){

    private lateinit var binding: FragmentListLoansBinding
    private lateinit var viewModel: ListLoansViewModel
    private lateinit var adapter: LoansAdapter

    @Inject lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListLoansBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, providerFactory).get(ListLoansViewModel::class.java)
        binding.viewModel = viewModel

        adapter = LoansAdapter(LoanListener { id -> viewModel.onLoanClicked(id) })
        binding.recyclerViewLoans.adapter = adapter

        setupObservers()
    }

    private fun setupObservers(){
        viewModel.loans.observe(this, Observer { list ->
            list?.let {
                binding.currentMonthLoans.text = setCurrentDate()
                adapter.submitList(list)
            }
        })

        viewModel.previousMonthBtnClicked.observe(this, Observer { clicked ->
            if(clicked){
                binding.currentMonthLoans.text = viewModel.updatePreviousMonth()
                viewModel.onPreviousMonthBtnClickFinished()
            }
        })

        viewModel.nextMonthBtnClicked.observe(this, Observer { clicked ->
            if(clicked){
                binding.currentMonthLoans.text = viewModel.updateNextMonth()
                viewModel.onNextMonthBtnClickFinished()
            }
        })

        viewModel.navToAddLoan.observe(this, Observer { navigate ->
            if(navigate){
                findNavController().navigate(ListLoansFragmentDirections.actionLoansFragmentToAddLoanFragment())
                viewModel.onNavigatedToAddLoan()
            }
        })

        viewModel.navToEditLoan.observe(this, Observer { id ->
            id?.let {
                findNavController().navigate(ListLoansFragmentDirections.actionLoansFragmentToEditLoanFragment(id))
                viewModel.onNavigatedToEditLoan()
            }
        })
    }
}