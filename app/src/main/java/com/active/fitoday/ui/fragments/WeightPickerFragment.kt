package com.active.fitoday.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.active.fitoday.databinding.FragmentUserWeightPickerBinding
import com.active.fitoday.ui.util.Enum

class WeightPickerFragment(val value: Enum.weightPickerType): Fragment() {

    lateinit var viewBinding: FragmentUserWeightPickerBinding
    private val weightPickerType = value


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentUserWeightPickerBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.ibWeightPickBack.setOnClickListener {
            activity?.onBackPressed()
        }

        if (weightPickerType == Enum.weightPickerType.currentWeight || weightPickerType == Enum.weightPickerType.updateWeight) {
            viewBinding.tvWeightPickerTitle.text = "What is your current weight?"
        }
        else {
            viewBinding.tvWeightPickerTitle.text = "What is your weight goal?"
        }

        viewBinding.btnWeightPickerConfirm.setOnClickListener {
            if (weightPickerType == Enum.weightPickerType.currentWeight) {
                val fragmentTransaction = parentFragmentManager.beginTransaction()
                fragmentTransaction.add(com.active.fitoday.R.id.tabFragmentContainer,  WeightPickerFragment(Enum.weightPickerType.goalWeight), "WeightPickerFragment").addToBackStack("WeightPickerFragment").commit()
            }
            else if (weightPickerType == Enum.weightPickerType.updateWeight) {
                activity?.onBackPressed()
            }
            else {
                val fragmentTransaction = parentFragmentManager.beginTransaction()
                fragmentTransaction.add(com.active.fitoday.R.id.tabFragmentContainer,  GoalAcheivedFragment(), "GoalAcheivedFragment").addToBackStack("WeightPickerFragment").commit()
            }
        }
    }
}