package com.active.fitody

import cn.icomon.icdevicemanager.model.data.ICRulerData
import cn.icomon.icdevicemanager.model.data.ICSkipData
import cn.icomon.icdevicemanager.model.data.ICWeightData

interface FitodyDeviceHelper {

    fun onReceivedSkipRopeData(skipData: ICSkipData) {}
    fun onReceivedMeasuringScaleData(measureData: ICRulerData) {}
    fun onReceiveWeightData(weightData: ICWeightData) {}

}