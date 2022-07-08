package com.active.fitody.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.active.fitody.R
import com.active.fitody.UserPrefs
import com.active.fitody.databinding.FragmentSetWeightGoalBinding
import com.active.fitody.util.Enum

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
            UserPrefs.getInstance().putUserGoalType(Enum.weightGoalType.lose.value)
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragmentContainer, WeightPickerFragment(Enum.weightPickerType.currentWeight), "SetWeightGoalFragment").addToBackStack("HomeFragment").commit()
        }

        viewBinding.btnGainWeight.setOnClickListener {
            UserPrefs.getInstance().putUserGoalType(Enum.weightGoalType.gain.value)
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragmentContainer,  WeightPickerFragment(Enum.weightPickerType.currentWeight), "SetWeightGoalFragment").addToBackStack("HomeFragment").commit()
        }
    }
}