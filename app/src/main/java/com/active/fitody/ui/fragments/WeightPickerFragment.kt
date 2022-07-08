package com.active.fitody.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.active.fitody.R
import com.active.fitody.UserPrefs
import com.active.fitody.databinding.FragmentUserWeightPickerBinding
import com.active.fitody.util.Enum

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

        val unitList = arrayListOf<String>("kg", "lbs")
        viewBinding.npUnitPicker.displayedValues = unitList.toTypedArray()

        viewBinding.btnWeightPickerConfirm.setOnClickListener {
            if (weightPickerType == Enum.weightPickerType.currentWeight) {
                val weightInfo = "${viewBinding.npWeightPicker.value}.${viewBinding.npWeightDecimalValuePicker.value}"
                UserPrefs.getInstance().putUserCurrentWeight(weightInfo.toFloat())
                UserPrefs.getInstance().putUserInitialWeight(weightInfo.toFloat())
                val fragmentTransaction = parentFragmentManager.beginTransaction()
                fragmentTransaction.add(R.id.fragmentContainer,  WeightPickerFragment(Enum.weightPickerType.goalWeight), "WeightPickerFragment").addToBackStack("WeightPickerFragment").commit()
            }
            else if (weightPickerType == Enum.weightPickerType.goalWeight) {
                val weightInfo = "${viewBinding.npWeightPicker.value}.${viewBinding.npWeightDecimalValuePicker.value}"
                UserPrefs.getInstance().putUserWeightTarget(weightInfo.toFloat())
                activity?.finish()
            }
        }
    }
}