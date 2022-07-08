package com.active.fitody.util

import cn.icomon.icdevicemanager.model.other.ICConstant

class Enum {

    enum class bottomNavTabType(val value: Int) {
        home(0),
        exercise(1),
        devices(2),
        profile(3);
    }

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
        companion object {
            fun enumFromInt(value: Int): bodyProportionsType {
                return when(value) {
                    1 -> leftProportionItem
                    else -> rightProportionItem
                }
            }
        }
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

    enum class skipRopeActivityMode(val value: Int) {
        targetMode(1),
        countDownMode(2),
        freeJumpMode(3),
        timerMode(4);

        companion object {
            fun enumFromInt(value: Int): skipRopeActivityMode {
                var returnVal = skipRopeActivityMode.freeJumpMode
                when (value) {
                    1 -> returnVal = targetMode
                    2 -> returnVal = countDownMode
                    3 -> returnVal = freeJumpMode
                    4 -> returnVal = timerMode
                }

                return returnVal
            }

            fun enumFromString(value: Int): skipRopeActivityMode {
                var returnVal = skipRopeActivityMode.freeJumpMode
                when (value) {
                    1 -> returnVal = targetMode
                    2 -> returnVal = countDownMode
                    3 -> returnVal = freeJumpMode
                    4 -> returnVal = timerMode
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

    enum class bodyPartTypeEnum(val value: Int) {
        shoulder(1),
        bicep(2),
        chest(3),
        waist(4),
        hip(5),
        thigh(6),
        calf(7),
        neck(8),
        abdonen(9),
        bust(10);

        companion object {
            fun enumFromInt(value: Int): bodyPartTypeEnum {
                return when (value) {
                    1 -> shoulder
                    2 -> bicep
                    3 -> chest
                    4 -> waist
                    5 -> hip
                    6 -> thigh
                    7 -> calf
                    8 -> neck
                    9 -> abdonen
                    else -> bust
                }
            }
        }
    }

    enum class deviceType(val value: Int) {
        skippingRope(1),
        weightScale(2),
        rular(3),
        all(4);
        companion object {
            fun enumFromInt(value: Int): deviceType {
                var returnVal = skippingRope
                when (value) {
                    2 -> returnVal = weightScale
                    3 -> returnVal = rular
                    4 -> returnVal = all
                }

                return returnVal
            }

            fun enumFromDeviceType(value: ICConstant.ICDeviceType): deviceType {
                var returnVal = skippingRope
                when (value) {
                    ICConstant.ICDeviceType.ICDeviceTypeFatScale -> returnVal = weightScale
                    ICConstant.ICDeviceType.ICDeviceTypeRuler -> returnVal = rular
                }

                return returnVal
            }
        }
    }

    enum class userWeightUnit(val value: String) {
        kg("kg"),
        lbs("lbs");
        companion object {
            fun enumFromString(value: String): userWeightUnit {
                var returnVal = kg
                when (value) {
                    "kg" -> returnVal = kg
                    "lbs" -> returnVal = lbs
                }

                return returnVal
            }
        }
    }

    enum class userRularUnit(val value: String) {
        inch("inch"),
        cm("cm");
        companion object {
            fun enumFromString(value: String): userRularUnit {
                var returnVal = inch
                when (value) {
                    "inch" -> returnVal = inch
                    "cm" -> returnVal = cm
                }

                return returnVal
            }
        }
    }

    enum class userHeightUnit(val value: String) {
        inch("inch"),
        feet("feet");
        companion object {
            fun enumFromString(value: String): userHeightUnit {
                var returnVal = inch
                when (value) {
                    "inch" -> returnVal = inch
                    "feet" -> returnVal = feet
                }

                return returnVal
            }
        }
    }

    enum class weightGoalType(val value: Int) {
        lose(0),
        gain(1);
        companion object {
            fun enumFromString(value: Int): weightGoalType {
                var returnVal = lose
                when (value) {
                    0 -> returnVal = lose
                    1 -> returnVal = gain
                }

                return returnVal
            }
        }
    }

}