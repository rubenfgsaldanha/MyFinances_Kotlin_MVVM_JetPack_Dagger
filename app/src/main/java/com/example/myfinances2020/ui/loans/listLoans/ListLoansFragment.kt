package com.example.myfinances2020.ui.loans.listLoans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myfinances2020.R
import com.example.myfinances2020.databinding.FragmentListLoansBinding
import com.example.myfinances2020.utils.setCurrentDate
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListLoansFragment : Fragment() {

    private lateinit var binding: FragmentListLoansBinding
    private val viewModel by viewModel<ListLoansViewModel>()
    private lateinit var adapter: LoansAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListLoansBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel

        adapter = LoansAdapter(LoanListener { id -> viewModel.onLoanClicked(id) })
        binding.recyclerViewLoans.adapter = adapter

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.loans.observe(this, Observer { list ->
            list?.let {
                binding.currentMonthLoans.text = setCurrentDate()
                adapter.submitList(list)
            }
        })

        viewModel.previousMonthBtnClicked.observe(this, Observer { clicked ->
            if (clicked) {
                binding.currentMonthLoans.text = viewModel.updatePreviousMonth()
                viewModel.onPreviousMonthBtnClickFinished()
            }
        })

        viewModel.nextMonthBtnClicked.observe(this, Observer { clicked ->
            if (clicked) {
                binding.currentMonthLoans.text = viewModel.updateNextMonth()
                viewModel.onNextMonthBtnClickFinished()
            }
        })

        viewModel.navToAddLoan.observe(this, Observer { navigate ->
            if (navigate) {
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