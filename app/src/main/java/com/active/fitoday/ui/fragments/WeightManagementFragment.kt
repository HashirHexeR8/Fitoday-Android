package com.active.fitoday.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.active.fitoday.databinding.FragmentWeightManagementBinding
import com.jjoe64.graphview.series.LineGraphSeries

import android.R
import android.graphics.Color
import com.active.fitoday.ui.util.Enum

import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import io.github.farshidroohi.ChartEntity
import lecho.lib.hellocharts.model.Line
import lecho.lib.hellocharts.model.LineChartData

import lecho.lib.hellocharts.model.PointValue
import lecho.lib.hellocharts.view.LineChartView


class WeightManagementFragment(): Fragment() {

    lateinit var binding: FragmentWeightManagementBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWeightManagementBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chart = binding.weightGraphView

        val values: MutableList<PointValue> = ArrayList()
        values.add(PointValue(0F, 1F))
        values.add(PointValue(1F, 2F))
        values.add(PointValue(2F, 1F))
        values.add(PointValue(3F, 2F))

        chart.setInteractive(false)
        chart.setZoomType(null)
        chart.setContainerScrollEnabled(false, null)

        val line: Line = Line(values).setColor(Color.BLUE).setCubic(true)
        val lines: MutableList<Line> = ArrayList<Line>()
        lines.add(line)

        val data = LineChartData()
        data.lines = lines
        chart.lineChartData = data

        binding.tvUpdateWeight.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.add(com.active.fitoday.R.id.tabFragmentContainer, WeightPickerFragment(Enum.weightPickerType.updateWeight), "WeightManagementFragment").addToBackStack("HomeFragment").commit()
        }

        binding.tvEditGoal.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.add(com.active.fitoday.R.id.tabFragmentContainer, SetWeightGoalFragment(), "WeightManagementFragment").addToBackStack("HomeFragment").commit()
        }

        binding.ibWeightBack.setOnClickListener {
            activity?.onBackPressed()
        }

    }
}