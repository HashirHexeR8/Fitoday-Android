package com.active.fitody.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.active.fitody.R
import com.active.fitody.databinding.FragmentReadDeviceDataBottomSheetBinding
import com.active.fitody.util.Enum
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReadDeviceDataBottomSheetFragment: BottomSheetDialogFragment() {

    companion object {
        private var instance: ReadDeviceDataBottomSheetFragment? = null

        fun shareInstance(deviceType: Enum.deviceType): ReadDeviceDataBottomSheetFragment {
            if (instance == null) {
                instance = ReadDeviceDataBottomSheetFragment()
            }
            instance?.setDeviceType(deviceType)
            return instance!!
        }
    }

    lateinit var viewBinding: FragmentReadDeviceDataBottomSheetBinding
    private var mDeviceType: Enum.deviceType = Enum.deviceType.rular

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = false
        viewBinding = FragmentReadDeviceDataBottomSheetBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mDeviceType == Enum.deviceType.rular) {
            viewBinding.ivDeviceIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_smart_tape))
            viewBinding.tvDeviceName.text = "Fitody Smart Tape"
        }
        else if (mDeviceType == Enum.deviceType.weightScale) {
            viewBinding.ivDeviceIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_weight_management_shop))
            viewBinding.tvDeviceName.text = "Fitody Smart Scale"
        }
    }

    private fun setDeviceType(deviceType: Enum.deviceType) {
        this.mDeviceType = deviceType
    }
}