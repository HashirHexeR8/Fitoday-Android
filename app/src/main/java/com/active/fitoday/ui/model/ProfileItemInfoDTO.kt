package com.active.fitoday.ui.model

class ProfileItemInfoDTO {

    constructor()

    constructor(itemTitle: String, firstItemKey: String, firstItemValue: String, secondItemKey: String, secondItemValue: String, firstItemIcon: Int?, secondItemIcon: Int?, itemIconReq: Boolean, lastItem: Boolean, titleItem: Boolean) {
        this.profileItemTitle = itemTitle
        this.profileFirstItemKey = firstItemKey
        this.profileFirstItemValue = firstItemValue
        this.profileSecondItemKey = secondItemKey
        this.profileSecondItemValue = secondItemValue
        this.profileFirstItemIcon = firstItemIcon
        this.profileSecondItemIcon = secondItemIcon
        this.isItemIconRequired = itemIconReq
        this.isLastItem = lastItem
        this.isTitleItem = titleItem

    }

    var profileItemTitle: String = ""
    var profileFirstItemKey: String = ""
    var profileFirstItemValue: String = ""
    var profileSecondItemKey: String = ""
    var profileSecondItemValue: String = ""
    var profileFirstItemIcon: Int? = null
    var profileSecondItemIcon: Int? = null
    var isItemIconRequired: Boolean = true
    var isLastItem: Boolean = false
    var isTitleItem: Boolean = true
}