package com.example.myfinances2020.ui.transactions.listTransactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myfinances2020.R
import com.example.myfinances2020.databinding.FragmentListTransactionsBinding
import com.example.myfinances2020.utils.setCurrentDate
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListTransactionsFragment : Fragment() {

    private val viewModel by viewModel<ListTransactionsViewModel>()

    private lateinit var binding: FragmentListTransactionsBinding
    private lateinit var adapter: TransactionsAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListTransactionsBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel

        adapter = TransactionsAdapter(TransactionListener { id -> viewModel.onTransactionClicked(id) })
        binding.recycleViewTransactions.adapter = adapter

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.transactions.observe(this, Observer { list ->
            list?.let {
                binding.currentMonth.text = setCurrentDate()
                adapter.submitList(list)
            }
        })

        viewModel.previousMonthBtnClicked.observe(this, Observer { clicked ->
            if (clicked) {
                binding.currentMonth.text = viewModel.updatePreviousMonth()
                viewModel.onPreviousMonthBtnClickFinished()
            }
        })

        viewModel.nextMonthBtnClicked.observe(this, Observer { clicked ->
            if (clicked) {
                binding.currentMonth.text = viewModel.updateNextMonth()
                viewModel.onNextMonthBtnClickFinished()
            }
        })

        viewModel.navToAddTransaction.observe(this, Observer { navigate ->
            if (navigate) {
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