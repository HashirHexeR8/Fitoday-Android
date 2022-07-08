package com.active.fitody.ui.BodyProportionsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.active.fitody.R
import com.active.fitody.databinding.LayoutShopDevicesBannerItemBinding
import com.active.fitody.model.DevicesItemInfoDTO

class BannerPagerFragment(deviceItemInfo: DevicesItemInfoDTO) : Fragment() {

    private lateinit var binding: LayoutShopDevicesBannerItemBinding
    private val mDeviceInfo = deviceItemInfo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutShopDevicesBannerItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivShopDeviceBannerIcon.setImageDrawable(resources.getDrawable(mDeviceInfo.deviceIcon ?: R.drawable.ic_smart_weight_device))
        binding.tvShopDeviceBannerName.text = mDeviceInfo.deviceName
        binding.tvShopBannerDescription.text = mDeviceInfo.deviceDescription
    }
}