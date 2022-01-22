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
}