package com.example.myfinances2020.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myfinances2020.R
import com.example.myfinances2020.databinding.FragmentSettingsBinding
import com.example.myfinances2020.utils.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SettingsFragment : DaggerFragment(){

    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: FragmentSettingsBinding

    @Inject lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.settings)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, providerFactory).get(SettingsViewModel::class.java)
        binding.viewModel = viewModel

        populateCurrencies()

        setupObservers()
    }

    private fun setupObservers(){
        viewModel.showPercentage.observe(this, Observer { showPercentage ->
            binding.showPercentages.isChecked = showPercentage
            viewModel.onShowPercentagesClickFinished()
        })

        viewModel.showSubtitles.observe(this, Observer { showSubtitles ->
            binding.showSubtitles.isChecked = showSubtitles
            viewModel.onShowSubtitlesClickFinished()
        })
    }

    private fun populateCurrencies(){
        val currencyAdapter = ArrayAdapter.createFromResource(activity!!.applicationContext, R.array.currency, android.R.layout.simple_spinner_item)
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCurrency.adapter = currencyAdapter
    }
}