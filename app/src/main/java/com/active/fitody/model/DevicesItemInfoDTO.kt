package com.active.fitody.model

class DevicesItemInfoDTO {

    constructor(deviceIcon: Int?, deviceName: String, deviceDescription: String, devicePrice: String, deviceDiscount: String) {
        this.deviceIcon = deviceIcon
        this.deviceName = deviceName
        this.deviceDescription = deviceDescription
        this.devicePrice = devicePrice
        this.deviceDiscount = deviceDiscount
    }

    constructor() {

    }

    var deviceIcon: Int? = 0
    var deviceName: String = ""
    var deviceDescription: String = ""
    var devicePrice: String = ""
    var deviceDiscount: String = ""
}