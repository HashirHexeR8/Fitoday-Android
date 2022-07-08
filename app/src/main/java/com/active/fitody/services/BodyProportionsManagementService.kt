package com.active.fitody.services

import com.active.fitody.managers.BodyProportionsManager
import com.active.fitody.model.BodyProportionsItemInfoDTO

class BodyProportionsManagementService {

    fun persistBodyProportionsData(bodyProportionsItemInfo: BodyProportionsItemInfoDTO) {
        BodyProportionsManager.sharedInstance().persistBodyProportionMeasurements(bodyProportionsItemInfo)
    }

    fun getProportionsData(): ArrayList<BodyProportionsItemInfoDTO> {
        return BodyProportionsManager.sharedInstance().getProportionMeasurements()
            .map { BodyProportionsItemInfoDTO(it) } as ArrayList
    }
}