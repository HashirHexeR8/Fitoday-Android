package com.active.fitoday.ui.util

class Enum {
    enum class IntroPagerEnum(val value: Int)
    {
        enjoy(0),
        idk(1),
        run(2);

        companion object {
            fun enumFromInt(value: Int): IntroPagerEnum {
                var returnVal =  enjoy
                when (value) {
                    0 -> returnVal = enjoy
                    1 -> returnVal = idk
                    2 -> returnVal = run
                }

                return returnVal
            }
        }
    }

    enum class bodyProportionsType(val value: Int) {
        rightProportionItem(0),
        leftProportionItem(1);
    }

    enum class shopDevicesListType(val value: Int) {
        myDevicesList(0),
        shopDevicesList(1);
    }

    enum class settingsItemsType(val value: Int) {
        listItem(1),
        shopButtonItem(2),
        logoutButtonItem(3);
    }

    enum class jumpingRopeActivityMode(val value: Int) {
        targetMode(1),
        countDownMode(2),
        freeJumpMode(3);

        companion object {
            fun enumFromInt(value: Int): jumpingRopeActivityMode {
                var returnVal = jumpingRopeActivityMode.freeJumpMode
                when (value) {
                    1 -> returnVal = targetMode
                    2 -> returnVal = countDownMode
                    3 -> returnVal = freeJumpMode
                }

                return returnVal
            }
        }
    }

    enum class weightPickerType(val value: Int) {
        currentWeight(1),
        goalWeight(2),
        updateWeight(3);

        companion object {
            fun enumFromInt(value: Int): weightPickerType {
                var returnVal = weightPickerType.currentWeight
                when (value) {
                    1 -> returnVal = currentWeight
                    2 -> returnVal = goalWeight
                    3 -> returnVal = updateWeight
                }

                return returnVal
            }
        }
    }

}