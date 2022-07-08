package com.active.fitody.model

import com.active.fitody.util.Enum

class settingsListItemDTO {
    constructor()

    constructor(itemType: Enum.settingsItemsType, itemText: String) {
        this.itemText = itemText
        this.itemType = itemType
    }
    var itemType: Enum.settingsItemsType = Enum.settingsItemsType.listItem
    var itemText: String = ""
}