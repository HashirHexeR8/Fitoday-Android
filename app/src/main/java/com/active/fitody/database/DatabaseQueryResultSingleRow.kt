package com.active.fitody.database

class DatabaseQueryResultSingleRow: HashMap<String, Any>() {

    fun stringValue(key: String?): String {
        return try {
            val value = this[key]
            value?.toString() ?: ""
        } catch (exception: Exception) {
            ""
        }
    }

    fun longValue(key: String?): Long {
        var longValue: Long = 0
        try {
            var value = this[key]
            if (value != null) {
                if (value is String) {
                    value = java.lang.Long.valueOf(value.toString())
                }
                longValue = value as Long
            }
        } catch (exception: Exception) {
            longValue = 0
        }
        return longValue
    }

    fun intValue(key: String?): Int {
        return try {
            longValue(key).toInt()
        } catch (exception: Exception) {
            0
        }
    }

    fun boolValue(key: String?): Boolean {
        val intValue = longValue(key).toInt()
        return if (intValue == 1) true else false
    }

    fun floatValue(key: String?): Float {
        return try {
            val stringValue = stringValue(key)
            java.lang.Float.valueOf(stringValue)
        } catch (exception: Exception) {
            0F
        }
    }

    fun doubleValue(key: String?): Double {
        return try {
            val stringValue = stringValue(key)
            java.lang.Double.valueOf(stringValue)
        } catch (exception: Exception) {
            0.0
        }
    }
}