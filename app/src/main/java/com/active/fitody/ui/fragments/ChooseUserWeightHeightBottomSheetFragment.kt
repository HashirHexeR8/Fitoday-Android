package com.active.fitody.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.active.fitody.UserPrefs
import com.active.fitody.databinding.FragmentChooseUserWeightHeightBottomSheetBinding
import com.active.fitody.databinding.FragmentSelectUnitBottomSheetDialogBinding
import com.active.fitody.util.Enum
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseUserWeightHeightBottomSheetFragment: BottomSheetDialogFragment() {
    companion object {
        private var instance: ChooseUserWeightHeightBottomSheetFragment? = null

        fun shareInstance(isWeightChooser: Boolean, sheetDismissListener: WeightHeightChooserSheetListener): ChooseUserWeightHeightBottomSheetFragment {
            if (instance == null) {
                instance = ChooseUserWeightHeightBottomSheetFragment()
            }
            instance?.setIsWeightChooser(isWeightChooser, sheetDismissListener)
            return instance!!
        }
    }

    lateinit var viewBinding: FragmentChooseUserWeightHeightBottomSheetBinding
    private var mIsWeightChooser = false
    private lateinit var mSheetDismissListener: WeightHeightChooserSheetListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = false
        viewBinding = FragmentChooseUserWeightHeightBottomSheetBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mIsWeightChooser) {
            val unitList = arrayListOf<String>("kg", "lbs")
            viewBinding.npUnitPicker.displayedValues = unitList.toTypedArray()
        }
        else {
            val unitList = arrayListOf<String>("feet", "inch")
            viewBinding.npUnitPicker.displayedValues = unitList.toTypedArray()
        }
        viewBinding.btnCancel.setOnClickListener {
            dismiss()
            mSheetDismissListener.onSheetDismiss(mIsWeightChooser, false)
        }
        viewBinding.btnConfirm.setOnClickListener {
            if (mIsWeightChooser) {
                val weightInfo = "${viewBinding.npWeightPicker.value}.${viewBinding.npWeightDecimalValuePicker.value}"
                UserPrefs.getInstance().putUserCurrentWeight(weightInfo.toFloat())
                if (viewBinding.npUnitPicker.value == 0) {
                    UserPrefs.getInstance().putUserHeightUnit(Enum.userWeightUnit.kg.value)
                }
                else {
                    UserPrefs.getInstance().putUserHeightUnit(Enum.userWeightUnit.lbs.value)
                }
            }
            else {
                val heightInfo = "${viewBinding.npWeightPicker.value}.${viewBinding.npWeightDecimalValuePicker.value}"
                UserPrefs.getInstance().putUserHeight(heightInfo.toFloat())
                if (viewBinding.npUnitPicker.value == 0) {
                    UserPrefs.getInstance().putUserHeightUnit(Enum.userHeightUnit.feet.value)
                }
                else {
                    UserPrefs.getInstance().putUserHeightUnit(Enum.userHeightUnit.inch.value)
                }
            }
            dismiss()
            mSheetDismissListener.onSheetDismiss(mIsWeightChooser, true)
        }
    }

    private fun setIsWeightChooser(isWeightChooser: Boolean, sheetDismissListener: WeightHeightChooserSheetListener) {
        mIsWeightChooser = isWeightChooser
        mSheetDismissListener = sheetDismissListener
    }
}

interface WeightHeightChooserSheetListener {
    fun onSheetDismiss(isWeightChooser: Boolean, isSuccess: Boolean)
}
