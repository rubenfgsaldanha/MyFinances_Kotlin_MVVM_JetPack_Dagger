package com.example.myfinances2020.ui.transactions

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
import com.example.myfinances2020.databinding.FragmentTransactionsBinding
import com.example.myfinances2020.utils.formatDateWithoutDay
import com.example.myfinances2020.utils.getCurrentDate
import java.util.*

class TransactionsFragment : Fragment(){

    private lateinit var binding: FragmentTransactionsBinding
    private lateinit var viewModel: TransactionsViewModel
    private lateinit var adapter: TransactionsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTransactionsBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = TransactionsViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TransactionsViewModel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        adapter = TransactionsAdapter(TransactionListener { id -> viewModel.onTransactionClicked(id) })
        binding.recycleViewTransactions.adapter = adapter

        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.transactions.observe(this, Observer { list ->
            list?.let {
                val c = getCurrentDate()
                binding.currentMonth.text = formatDateWithoutDay(c.get(Calendar.MONTH), c.get(Calendar.YEAR))
                adapter.submitList(list)
            }
        })

        viewModel.previousMonthBtnClicked.observe(this, Observer { clicked ->
            if(clicked){
                binding.currentMonth.text = viewModel.updatePreviousMonth()
                viewModel.onPreviousMonthBtnClickFinished()
            }
        })

        viewModel.nextMonthBtnClicked.observe(this, Observer { clicked ->
            if(clicked){
                binding.currentMonth.text = viewModel.updateNextMonth()
                viewModel.onNextMonthBtnClickFinished()
            }
        })

        viewModel.navToAddTransaction.observe(this, Observer { navigate ->
            if(navigate){
                this.findNavController().navigate(TransactionsFragmentDirections.actionTransactionsFragmentToAddTransactionFragment())
                viewModel.onNavigatedToAddTransaction()
            }
        })

        viewModel.navToEditTransaction.observe(this, Observer { navigate ->
            navigate?.let { id ->
                this.findNavController().navigate(TransactionsFragmentDirections.actionTransactionsFragmentToEditTransactionFragment(id))
                viewModel.onNavigatedToEditTransaction()
            }
        })
    }
}