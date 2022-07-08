package com.active.fitody

import android.util.Log
import cn.icomon.icdevicemanager.ICDeviceManagerDelegate
import cn.icomon.icdevicemanager.model.data.*
import cn.icomon.icdevicemanager.model.device.ICDevice
import cn.icomon.icdevicemanager.model.device.ICDeviceInfo
import cn.icomon.icdevicemanager.model.other.ICConstant
import com.active.fitody.model.BluetoothDeviceInfoDTO
import com.active.fitody.util.Constants
import com.active.fitody.util.Enum

class FitodyDeviceManager: ICDeviceManagerDelegate {

    private var mDeviceDataHelper: FitodyDeviceHelper? = null
    private var connectedDevice: BluetoothDeviceInfoDTO? = null

    private constructor()

    companion object {
        private var instance: FitodyDeviceManager? = null
        fun getInstance() : FitodyDeviceManager
        {
            if (instance == null) {
                instance = FitodyDeviceManager()
            }
            return instance!!
        }
    }

    public fun getConnectedDevice(deviceType: Enum.deviceType): BluetoothDeviceInfoDTO? {
        return when (deviceType) {
            Enum.deviceType.weightScale -> {
                if (connectedDevice?.deviceType == Enum.deviceType.weightScale) connectedDevice else null
            }
            Enum.deviceType.rular -> {
                if (connectedDevice?.deviceType == Enum.deviceType.rular) connectedDevice else null
            }
            Enum.deviceType.all -> {
                connectedDevice
            }
            else -> {
                if (connectedDevice?.deviceType == Enum.deviceType.skippingRope) connectedDevice else null
            }
        }
    }

    public fun setConnectedDevice(device: BluetoothDeviceInfoDTO?) {
        connectedDevice = device
    }

    public fun subscribeDeviceUpdates(deviceDataHelper: FitodyDeviceHelper) {
        this.mDeviceDataHelper = deviceDataHelper
    }

    public fun unsubscribeDeviceUpdates() {
        this.mDeviceDataHelper = null
    }


    override fun onInitFinish(status: Boolean) {
        if (status)
            Log.i(Constants.DEVICE_SDK_LOG, "Device Init success")
        else {
            Log.i(Constants.DEVICE_SDK_LOG, "Device Init failure")
        }
    }

    override fun onBleState(p0: ICConstant.ICBleState?) {

    }

    override fun onDeviceConnectionChanged(p0: ICDevice?, p1: ICConstant.ICDeviceConnectState?) {
        Log.i("devMac:${p0?.macAddr}", p1?.name!!)
    }

    override fun onReceiveWeightData(p0: ICDevice?, p1: ICWeightData) {
        mDeviceDataHelper?.onReceiveWeightData(p1)
    }

    override fun onReceiveKitchenScaleData(p0: ICDevice?, p1: ICKitchenScaleData?) {
    }

    override fun onReceiveKitchenScaleUnitChanged(p0: ICDevice?, p1: ICConstant.ICKitchenScaleUnit?) {
    }

    override fun onReceiveCoordData(p0: ICDevice?, p1: ICCoordData?) {
    }

    override fun onReceiveRulerData(p0: ICDevice?, p1: ICRulerData) {
        mDeviceDataHelper?.onReceivedMeasuringScaleData(p1)
    }

    override fun onReceiveRulerHistoryData(p0: ICDevice?, p1: ICRulerData?) {
    }

    override fun onReceiveWeightCenterData(p0: ICDevice?, p1: ICWeightCenterData?) {
    }

    override fun onReceiveWeightUnitChanged(p0: ICDevice?, p1: ICConstant.ICWeightUnit?) {
    }

    override fun onReceiveRulerUnitChanged(p0: ICDevice?, p1: ICConstant.ICRulerUnit?) {
    }

    override fun onReceiveRulerMeasureModeChanged(p0: ICDevice?, p1: ICConstant.ICRulerMeasureMode?) {
    }

    override fun onReceiveMeasureStepData(p0: ICDevice?, p1: ICConstant.ICMeasureStep?, p2: Any?) {
    }

    override fun onReceiveWeightHistoryData(p0: ICDevice?, p1: ICWeightHistoryData?) {
    }

    override fun onReceiveSkipData(p0: ICDevice?, p1: ICSkipData) {
        mDeviceDataHelper?.onReceivedSkipRopeData(p1)
    }

    override fun onReceiveHistorySkipData(p0: ICDevice?, p1: ICSkipData?) {
    }

    override fun onReceiveSkipBattery(p0: ICDevice?, p1: Int) {
    }

    override fun onReceiveUpgradePercent(p0: ICDevice?, p1: ICConstant.ICUpgradeStatus?, p2: Int) {
    }

    override fun onReceiveDeviceInfo(p0: ICDevice?, p1: ICDeviceInfo?) {
    }

    override fun onReceiveDebugData(p0: ICDevice?, p1: Int, p2: Any?) {
    }

    override fun onReceiveConfigWifiResult(p0: ICDevice?, p1: ICConstant.ICConfigWifiState?) {
    }
}