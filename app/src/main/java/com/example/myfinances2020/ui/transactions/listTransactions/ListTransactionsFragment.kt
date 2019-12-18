package com.example.myfinances2020.ui.transactions.listTransactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.myfinances2020.R
import com.example.myfinances2020.databinding.FragmentListTransactionsBinding
import com.example.myfinances2020.utils.ViewModelProviderFactory
import com.example.myfinances2020.utils.formatDateWithoutDay
import com.example.myfinances2020.utils.getCurrentDate
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject

class ListTransactionsFragment : DaggerFragment(){

    private lateinit var binding: FragmentListTransactionsBinding
    private lateinit var viewModel: ListTransactionsViewModel
    private lateinit var adapter: TransactionsAdapter

    @Inject lateinit var providerFactory: ViewModelProviderFactory


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListTransactionsBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, providerFactory).get(ListTransactionsViewModel::class.java)
        binding.viewModel = viewModel

        adapter = TransactionsAdapter(TransactionListener { id -> viewModel.onTransactionClicked(id) })
        binding.recycleViewTransactions.adapter = adapter

        setupObservers()
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
                findNavController().navigate(ListTransactionsFragmentDirections.actionTransactionsFragmentToAddTransactionFragment())
                viewModel.onNavigatedToAddTransaction()
            }
        })

        viewModel.navToEditTransaction.observe(this, Observer { navigate ->
            navigate?.let { id ->
                findNavController().navigate(ListTransactionsFragmentDirections.actionTransactionsFragmentToEditTransactionFragment(id))
                viewModel.onNavigatedToEditTransaction()
            }
        })
    }
}