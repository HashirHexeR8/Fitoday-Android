package com.active.fitoday.ui.model

class BodyProportionsItemInfoDTO {
    constructor()

    constructor(partName: String, partValue: String) {
        this.bodyPartProportionItemName = partName
        this.bodyPartMeasurementValue = partValue
    }

    var bodyPartProportionItemName: String = ""
    var bodyPartMeasurementValue: String = ""
}