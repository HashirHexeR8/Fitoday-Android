package com.active.fitoday.ui.BodyProportionsFragment

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.active.fitoday.R
import com.active.fitoday.databinding.LayoutShopDevicesBannerItemBinding
import com.active.fitoday.ui.model.DevicesItemInfoDTO
import com.active.fitoday.ui.util.Enum

class BannerPagerFragment(deviceItemInfo: DevicesItemInfoDTO) : Fragment() {

    private lateinit var binding: LayoutShopDevicesBannerItemBinding
    private val mDeviceInfo = deviceItemInfo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutShopDevicesBannerItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvShopDeviceBannerName.text = mDeviceInfo.deviceName
        binding.tvShopBannerDescription.text = mDeviceInfo.deviceDescription
    }
}