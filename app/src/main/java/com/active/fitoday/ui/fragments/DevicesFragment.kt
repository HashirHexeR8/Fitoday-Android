package com.active.fitoday.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.active.fitoday.R
import com.active.fitoday.databinding.FragmentDevicesBinding
import com.active.fitoday.databinding.MyDeviceRecyclerViewItemBinding
import com.active.fitoday.databinding.ShopDeviceRecyclerViewItemBinding
import com.active.fitoday.ui.BodyProportionsFragment.BannerPagerFragment
import com.active.fitoday.ui.model.DevicesItemInfoDTO
import com.active.fitoday.ui.model.settingsListItemDTO
import com.active.fitoday.ui.util.Enum

class DevicesFragment: Fragment() {

    lateinit var binding: FragmentDevicesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDevicesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvUserDevices.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val connectedDeviceRecyclerViewAdapter = DevicesFragmentRecyclerViewAdapter(Enum.shopDevicesListType.myDevicesList)
        binding.rvUserDevices.adapter = connectedDeviceRecyclerViewAdapter
        binding.rvShopDevices.layoutManager = LinearLayoutManager(context)
        val shopDeviceRecyclerViewAdapter = DevicesFragmentRecyclerViewAdapter(Enum.shopDevicesListType.shopDevicesList)
        binding.rvShopDevices.adapter = shopDeviceRecyclerViewAdapter
        val dataSource = ArrayList<DevicesItemInfoDTO>()
        dataSource.add(DevicesItemInfoDTO(R.drawable.ic_smart_weight_device, "Fitody Smart Scale", "Full Body Composition Including Body Fat, BMI, Water Percentage, Muscle & Bone Mass", "$13.99", "20% off"))
        dataSource.add(DevicesItemInfoDTO(R.drawable.ic_smart_jump_rope, "Fitody Smart Tape", "Optimize Your Fitness Performance With Precise Measuring & Tracking", "$13.99", "20% off"))
        dataSource.add(DevicesItemInfoDTO(R.drawable.ic_smart_tape, "Fitody Smart Rope", "Optimize Your Fitness Performance With Precise Measuring & Tracking", "$13.99", "20% off"))
        dataSource.add(DevicesItemInfoDTO(R.drawable.ic_smart_weight_device, "Fitody Smart BMI", "Optimize Your Fitness Performance With Precise Measuring & Tracking", "$13.99", "20% off"))
        shopDeviceRecyclerViewAdapter.setDataSource(dataSource)
        connectedDeviceRecyclerViewAdapter.setDataSource(dataSource)
        val adapter = DeviceBannerViewPagerAdapter(activity as AppCompatActivity, dataSource)
        binding.vpDevices.adapter = adapter
        binding.devicesPagerIndicator.setViewPager(binding.vpDevices)
        binding.devicesPagerIndicator.createIndicators(dataSource.size,0)
        adapter.registerAdapterDataObserver(binding.devicesPagerIndicator.adapterDataObserver)
    }
}

class DevicesFragmentRecyclerViewAdapter(itemType: Enum.shopDevicesListType): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val mItemType = itemType
    var mItemsDataSource = ArrayList<DevicesItemInfoDTO>()

    public fun setDataSource(itemsList: ArrayList<DevicesItemInfoDTO>)
    {
        mItemsDataSource = itemsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (mItemType == Enum.shopDevicesListType.myDevicesList) {
            val binding = MyDeviceRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyDevicesItemViewHolder(binding)
        }
        else {
            val binding = ShopDeviceRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ShopDevicesItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ShopDevicesItemViewHolder) {
            holder.bindItemInfo(mItemsDataSource[position])
        }
        else if (holder is MyDevicesItemViewHolder) {
            holder.bindItemInfo(mItemsDataSource[position])
        }
    }

    override fun getItemCount(): Int {
        return mItemsDataSource.size
    }

}

class MyDevicesItemViewHolder(val itemViewBinding: MyDeviceRecyclerViewItemBinding): RecyclerView.ViewHolder(itemViewBinding.root) {

    fun bindItemInfo(itemInfo: DevicesItemInfoDTO)
    {
        itemViewBinding.ivUserDeviceIcon.setImageDrawable(itemViewBinding.root.resources.getDrawable(itemInfo.deviceIcon ?: R.drawable.ic_smart_weight_device))
    }

}

class ShopDevicesItemViewHolder(private val itemViewBinding: ShopDeviceRecyclerViewItemBinding): RecyclerView.ViewHolder(itemViewBinding.root) {

    fun bindItemInfo(itemInfo: DevicesItemInfoDTO) {
        itemViewBinding.ivDeviceIcon.setImageDrawable(itemViewBinding.root.resources.getDrawable(itemInfo.deviceIcon ?: R.drawable.ic_smart_weight_device))
        itemViewBinding.tvShopDeviceName.text = itemInfo.deviceName
        itemViewBinding.tvShopDeviceDescription.text = itemInfo.deviceDescription
        itemViewBinding.tvShopDevicePrice.text = itemInfo.devicePrice
        itemViewBinding.tvShopDeviceDiscount.text = itemInfo.deviceDiscount
    }
}

class DeviceBannerViewPagerAdapter(activity: AppCompatActivity, devicesList: ArrayList<DevicesItemInfoDTO>) : FragmentStateAdapter(activity) {

    private var mDevicesList = devicesList

    override fun getItemCount(): Int = mDevicesList.size

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        return BannerPagerFragment(mDevicesList[position])
    }
}