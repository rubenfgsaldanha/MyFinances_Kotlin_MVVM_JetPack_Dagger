package com.example.myfinances2020.ui.graphs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myfinances2020.R
import com.example.myfinances2020.databinding.FragmentGraphsBinding
import com.example.myfinances2020.utils.ViewModelProviderFactory
import com.example.myfinances2020.utils.setCurrentDate
import dagger.android.support.DaggerFragment
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import javax.inject.Inject

class GraphsFragment : DaggerFragment() {

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

        binding.currentMonthGraphs.text = setCurrentDate()

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.transactions.observe(this, Observer { list ->
            list?.let { viewModel.pieChartLogic() }
        })

        viewModel.pieDataList.observe(this, Observer { pieData ->
            fillPieChart(pieData)
        })

        viewModel.previousMonthBtnClicked.observe(this, Observer { clicked ->
            if (clicked) {
                binding.currentMonthGraphs.text = viewModel.updatePreviousMonth()
                viewModel.onPreviousMonthBtnClickFinished()
            }
        })

        viewModel.nextMonthBtnClicked.observe(this, Observer { clicked ->
            if (clicked) {
                binding.currentMonthGraphs.text = viewModel.updateNextMonth()
                viewModel.onNextMonthBtnClickFinished()
            }
        })
    }

    private fun fillPieChart(data: List<SliceValue>?) {
        val pieChartData: PieChartData

        if (data.isNullOrEmpty()) {
            pieChartData = PieChartData()
            binding.noRecordsGraphs.text = getString(R.string.no_data_found)
            binding.overallGraphs.text = ""
        } else {
            pieChartData = PieChartData(data)
            binding.noRecordsGraphs.visibility = View.GONE
            binding.overallGraphs.text = viewModel.valueTotalAmount
        }

        binding.pieChart.pieChartData = pieChartData
        pieChartData.setHasLabels(true).valueLabelTextSize = 14
        pieChartData.setHasCenterCircle(true).centerCircleScale = 0.42f
    }
}