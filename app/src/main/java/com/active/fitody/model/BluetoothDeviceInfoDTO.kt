package com.active.fitody.model

import android.os.Parcel
import android.os.Parcelable
import cn.icomon.icdevicemanager.model.device.ICScanDeviceInfo
import com.active.fitody.util.Enum

class BluetoothDeviceInfoDTO : Parcelable {
    constructor() {}

    constructor(deviceInfo: ICScanDeviceInfo) {
        this.deviceName = deviceInfo.name
        this.deviceMacAddress = deviceInfo.macAddr
        this.deviceType = Enum.deviceType.enumFromDeviceType(deviceInfo.type)
    }
    var isConnected: Boolean = false
    var deviceName: String = ""
    var deviceMacAddress: String = ""
    var deviceType: Enum.deviceType = Enum.deviceType.skippingRope

    constructor(parcel: Parcel) : this() {
        isConnected = parcel.readByte() != 0.toByte()
        deviceName = parcel.readString() ?: ""
        deviceMacAddress = parcel.readString() ?: ""
        deviceType = Enum.deviceType.enumFromInt(parcel.readInt())
    }

    override fun equals(other: Any?): Boolean {
        val obj = other as BluetoothDeviceInfoDTO
        if (this.deviceMacAddress.lowercase().equals(obj.deviceMacAddress.lowercase())) {
            return true
        }
        return false
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (isConnected) 1 else 0)
        parcel.writeString(deviceName)
        parcel.writeString(deviceMacAddress)
        parcel.writeInt(deviceType.value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BluetoothDeviceInfoDTO> {
        override fun createFromParcel(parcel: Parcel): BluetoothDeviceInfoDTO {
            return BluetoothDeviceInfoDTO(parcel)
        }

        override fun newArray(size: Int): Array<BluetoothDeviceInfoDTO?> {
            return arrayOfNulls(size)
        }
    }

}