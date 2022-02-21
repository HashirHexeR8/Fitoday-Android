package com.active.fitoday.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.active.fitoday.databinding.FragmentSetWeightGoalBinding
import com.active.fitoday.ui.util.Enum

class SetWeightGoalFragment: Fragment() {

    lateinit var viewBinding: FragmentSetWeightGoalBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentSetWeightGoalBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.ibWeightGoalBack.setOnClickListener {
            activity?.onBackPressed()
        }

        viewBinding.btnLoseWeight.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.add(com.active.fitoday.R.id.tabFragmentContainer, WeightPickerFragment(Enum.weightPickerType.currentWeight), "SetWeightGoalFragment").addToBackStack("HomeFragment").commit()
        }

        viewBinding.btnGainWeight.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.add(com.active.fitoday.R.id.tabFragmentContainer,  WeightPickerFragment(Enum.weightPickerType.currentWeight), "SetWeightGoalFragment").addToBackStack("HomeFragment").commit()
        }
    }
}