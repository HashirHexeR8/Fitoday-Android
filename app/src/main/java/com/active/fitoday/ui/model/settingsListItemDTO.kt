package com.active.fitoday.ui.model

import com.active.fitoday.ui.util.Enum

class settingsListItemDTO {
    constructor()

    constructor(itemType: Enum.settingsItemsType, itemText: String) {
        this.itemText = itemText
        this.itemType = itemType
    }
    var itemType: Enum.settingsItemsType = Enum.settingsItemsType.listItem
    var itemText: String = ""
}