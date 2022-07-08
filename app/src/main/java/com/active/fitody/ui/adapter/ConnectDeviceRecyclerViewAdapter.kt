package com.active.fitody.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.active.fitody.databinding.LayoutNewDeviceRowItemBinding
import com.active.fitody.model.BluetoothDeviceInfoDTO

class ConnectDeviceRecyclerViewAdapter(
    private val onDeviceSelected: (BluetoothDeviceInfoDTO) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mDevicesDataSource: ArrayList<BluetoothDeviceInfoDTO> = ArrayList()

    public fun setDataSource(dataSource: ArrayList<BluetoothDeviceInfoDTO>) {
        mDevicesDataSource = dataSource
        notifyDataSetChanged()
    }

    public fun addToDataSource(newDevice: BluetoothDeviceInfoDTO) {
        val oldIndex = mDevicesDataSource.size - 1
        mDevicesDataSource.add(newDevice)
        notifyItemRangeInserted(oldIndex, 1)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val connectDeviceViewBinding = LayoutNewDeviceRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConnectDeviceItemViewHolder(connectDeviceViewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ConnectDeviceItemViewHolder) {
            val itemInfo = mDevicesDataSource[position]
            holder.bindItemInfo(itemInfo)
            holder.itemView.setOnClickListener {
                if (!itemInfo.isConnected) {
                    itemInfo.isConnected = true
                    onDeviceSelected.invoke(mDevicesDataSource[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mDevicesDataSource.size
    }

    inner class ConnectDeviceItemViewHolder(private val itemViewBinding: LayoutNewDeviceRowItemBinding): RecyclerView.ViewHolder(itemViewBinding.root) {
        fun bindItemInfo(deviceInfo: BluetoothDeviceInfoDTO) {
            itemViewBinding.tvBtDeviceName.text = deviceInfo.deviceName
            itemViewBinding.tvBtDeviceMacAddress.text = deviceInfo.deviceMacAddress
        }
    }
}