package com.active.fitoday.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.active.fitoday.databinding.FragmentWeightManagementBinding
import com.active.fitoday.ui.util.Enum
import lecho.lib.hellocharts.model.*


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
        values.add(PointValue(84.2F, 1F))
        values.add(PointValue(84F, 2F))
        values.add(PointValue(83.5F, 3F))
        values.add(PointValue(83F, 4F))


        chart.setInteractive(false)
        chart.setZoomType(null)
        chart.setContainerScrollEnabled(false, null)

        val line: Line = Line(values).setColor(Color.parseColor("#EF426F")).setCubic(true)
        line.pointColor = Color.parseColor("#EF426F")
        val lines: MutableList<Line> = ArrayList<Line>()
        lines.add(line)

        val data = LineChartData()
        data.lines = lines
        chart.lineChartData = data

//        val ymax: Float = 85f
//        val v = Viewport(chart.maximumViewport)
//        v.top = ymax //max value
//
//        v.bottom = 0f //min value
//
//        chart.maximumViewport = v
//        chart.currentViewport = v

        val axisValuesForX: ArrayList<AxisValue> = ArrayList<AxisValue>()
        val axisValuesForY: ArrayList<AxisValue> = ArrayList<AxisValue>()


        var i = 10f
        for (index in 1..10) {
            axisValuesForX.add(AxisValue(i))
            axisValuesForY.add(AxisValue(i))
            i += 10f
        }

        val axeX = Axis(axisValuesForX)
        axeX.lineColor = Color.BLACK
        axeX.textColor = Color.BLACK
        axeX.name = "Weight"

        val axeY = Axis(axisValuesForX)
        axeY.lineColor = Color.BLACK
        axeY.textColor = Color.BLACK
        axeY.name = "Days"

        data.axisXBottom = axeX
        data.axisYLeft = axeY

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