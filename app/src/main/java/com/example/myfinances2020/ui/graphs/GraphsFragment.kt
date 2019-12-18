package com.example.myfinances2020.ui.graphs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myfinances2020.R
import com.example.myfinances2020.databinding.FragmentGraphsBinding
import com.example.myfinances2020.utils.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class GraphsFragment : DaggerFragment(){

    private lateinit var viewModel: GraphsViewModel
    private lateinit var binding: FragmentGraphsBinding

    @Inject lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGraphsBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, providerFactory).get(GraphsViewModel::class.java)
        binding.viewModel = viewModel

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.previousMonthBtnClicked.observe(this, Observer { clicked ->
            if(clicked){
                binding.currentMonthGraphs.text = viewModel.updatePreviousMonth()
                viewModel.onPreviousMonthBtnClickFinished()
            }
        })

        viewModel.nextMonthBtnClicked.observe(this, Observer { clicked ->
            if(clicked){
                binding.currentMonthGraphs.text = viewModel.updateNextMonth()
                viewModel.onNextMonthBtnClickFinished()
            }
        })
    }
}