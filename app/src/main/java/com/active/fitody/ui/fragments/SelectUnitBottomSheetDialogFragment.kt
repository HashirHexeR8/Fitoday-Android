package com.active.fitody.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.active.fitody.R
import com.active.fitody.UserPrefs
import com.active.fitody.databinding.FragmentSelectUnitBottomSheetDialogBinding
import com.active.fitody.util.Enum
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectUnitBottomSheetDialogFragment: BottomSheetDialogFragment() {
    companion object {
        private var instance: SelectUnitBottomSheetDialogFragment? = null

        fun shareInstance(deviceType: Enum.deviceType): SelectUnitBottomSheetDialogFragment {
            if (instance == null) {
                instance = SelectUnitBottomSheetDialogFragment()
            }
            instance?.setDeviceType(deviceType)
            return instance!!
        }
    }

    lateinit var viewBinding: FragmentSelectUnitBottomSheetDialogBinding
    private var mDeviceType: Enum.deviceType = Enum.deviceType.rular

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = false
        viewBinding = FragmentSelectUnitBottomSheetDialogBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mDeviceType == Enum.deviceType.rular) {
            viewBinding.rbFirst.text = "inch"
            viewBinding.rbSecond.text = "cm"
            val currentSelected = UserPrefs.getInstance().getUserTapeUnit()
            if (currentSelected == Enum.userRularUnit.inch) {
                viewBinding.rbFirst.isChecked = true
            }
            else {
                viewBinding.rbSecond.isChecked = true
            }
        }
        else if (mDeviceType == Enum.deviceType.weightScale) {
            viewBinding.rbFirst.text = "kg"
            viewBinding.rbSecond.text = "lbs"
            val currentSelected = UserPrefs.getInstance().getUserWeightUnit()
            if (currentSelected == Enum.userWeightUnit.kg) {
                viewBinding.rbFirst.isChecked = true
            }
            else {
                viewBinding.rbSecond.isChecked = true
            }
        }
        viewBinding.rgUnit.setOnCheckedChangeListener { radioGroup, i ->
            if (i == viewBinding.rbFirst.id) {
                if (mDeviceType == Enum.deviceType.rular) {
                    UserPrefs.getInstance().putUserTapeUnit(Enum.userRularUnit.inch.value)
                }
                else if (mDeviceType == Enum.deviceType.weightScale) {
                    UserPrefs.getInstance().putUserWeightUnit(Enum.userWeightUnit.kg.value)
                }
            }
            else {
                if (mDeviceType == Enum.deviceType.rular) {
                    UserPrefs.getInstance().putUserTapeUnit(Enum.userRularUnit.cm.value)
                }
                else if (mDeviceType == Enum.deviceType.weightScale) {
                    UserPrefs.getInstance().putUserWeightUnit(Enum.userWeightUnit.lbs.value)
                }
            }
            dismiss()
        }
    }

    private fun setDeviceType(deviceType: Enum.deviceType) {
        this.mDeviceType = deviceType
    }
}