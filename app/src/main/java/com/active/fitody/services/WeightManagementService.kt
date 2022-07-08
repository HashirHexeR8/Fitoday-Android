package com.active.fitody.services

import cn.icomon.icdevicemanager.model.data.ICWeightData
import com.active.fitody.UserPrefs
import com.active.fitody.managers.UserWeightManager
import com.active.fitody.model.UserWeightInfoDTO
import com.active.fitody.util.Utilities
import java.math.RoundingMode
import java.text.DecimalFormat

class WeightManagementService {
    fun persistUserWeightData(weightData: ICWeightData) {
        val userWeightInfo = UserWeightInfoDTO()
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        userWeightInfo.userId = UserPrefs.getInstance().getUserId()
        userWeightInfo.userWeightKg = df.format(weightData.weight_kg).toDouble()
        userWeightInfo.userWeightLb = df.format(weightData.weight_lb).toDouble()
        userWeightInfo.weightMeasurementDate = Utilities.instance.getCurrentDate().toInt()
        userWeightInfo.dayOfWeek = Utilities.instance.getCurrentDay()
        UserWeightManager.sharedInstance().addUserWeightEntry(userWeightInfo)
    }

    fun getUserWeightHistory(): ArrayList<UserWeightInfoDTO> {
        var weightHistoryList = ArrayList<UserWeightInfoDTO>()
        weightHistoryList = UserWeightManager.sharedInstance().getUserWeightHistory().map { UserWeightInfoDTO(it) } as ArrayList<UserWeightInfoDTO>
        return weightHistoryList
    }
}