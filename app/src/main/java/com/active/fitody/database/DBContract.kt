package com.fiction.securetype.database

import android.content.ContentUris
import android.provider.BaseColumns


class DBContract {
    interface UserWeightInfoTableColumn {
        companion object {
            val columnUserId = "user_id"
            val columnUserWeightKg = "user_weight_kg"
            val columnUserWeightLb = "user_weight_Lb"
            val columnWeightMeasureDate = "weight_measure_date"
            val columnWeightDay = "weight_day"
        }
    }

    object UserWeightInfoTable : UserWeightInfoTableColumn {
        val tableName = "user_weight_info"
        val CREATE_TABLE_QUERY = "CREATE TABLE " +
                "$tableName (${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${UserWeightInfoTableColumn.columnUserId} INTEGER, " +
                "${UserWeightInfoTableColumn.columnUserWeightKg} TEXT, " +
                "${UserWeightInfoTableColumn.columnUserWeightLb} TEXT, " +
                "${UserWeightInfoTableColumn.columnWeightDay} TEXT, " +
                "${UserWeightInfoTableColumn.columnWeightMeasureDate} TEXT, " +
                "UNIQUE(${UserWeightInfoTableColumn.columnUserId}, ${UserWeightInfoTableColumn.columnWeightDay}, ${UserWeightInfoTableColumn.columnWeightMeasureDate}) ON CONFLICT REPLACE);"
    }

    interface SkipRopeActivityRecordTableColumn {
        companion object {
            val columnUserId = "user_id"
            val columnTotalSkips = "total_skips"
            val columnSkipMode = "skip_mode"
            val columnTimeElapsed = "time_elapsed"
            val columnCaloriesBurned = "calories_burned"
            val columnSkipActivityDate = "skip_activity_date"
        }
    }

    object SkipRopeActivityRecordTable : SkipRopeActivityRecordTableColumn {
        val tableName = "skip_rope_activity_record"
        val CREATE_TABLE_QUERY = "CREATE TABLE " +
                "$tableName (${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${SkipRopeActivityRecordTableColumn.columnUserId} INTEGER, " +
                "${SkipRopeActivityRecordTableColumn.columnTotalSkips} INTEGER, " +
                "${SkipRopeActivityRecordTableColumn.columnSkipMode} TEXT, " +
                "${SkipRopeActivityRecordTableColumn.columnTimeElapsed} TEXT, " +
                "${SkipRopeActivityRecordTableColumn.columnCaloriesBurned} TEXT, " +
                "${SkipRopeActivityRecordTableColumn.columnSkipActivityDate} TEXT)"
    }

    interface BodyProportionRecordTableColumn {
        companion object {
            val columnUserId = "user_id"
            val columnPartName = "part_name"
            val columnMeasurement = "measurement"
            val columnPropotionType = "body_proportion_type"
            val columnBodyPartType = "body_part_type"
        }
    }

    object BodyProportionRecordTable : BodyProportionRecordTableColumn {
        val tableName = "body_proportions_record"
        val CREATE_TABLE_QUERY = "CREATE TABLE " +
                "$tableName " +
                "(${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${BodyProportionRecordTableColumn.columnUserId} INTEGER, " +
                "${BodyProportionRecordTableColumn.columnPartName} TEXT, " +
                "${BodyProportionRecordTableColumn.columnMeasurement} TEXT, " +
                "${BodyProportionRecordTableColumn.columnPropotionType} INTEGER, " +
                "${BodyProportionRecordTableColumn.columnBodyPartType} INTEGER, " +
                "UNIQUE(${BodyProportionRecordTableColumn.columnUserId}, ${BodyProportionRecordTableColumn.columnPropotionType}, ${BodyProportionRecordTableColumn.columnBodyPartType}) ON CONFLICT REPLACE);"

    }

    interface UserEntryColumns {
        companion object {
            val columnUserId = "user_id"
            val columnUserName = "user_name"
            val columnUserEmail = "user_email"
            val columnUserPassword = "user_password"
            val columnUserBirthday = "user_birthday"
            val columnUserGender = "user_gender"
            val columnUserHeight = "user_height"
            val columnUserWeight = "user_weight"
        }
    }

    object UserEntryTable : UserEntryColumns {
        val tableName = "user_entry"
        val CREATE_TABLE_QUERY = "CREATE TABLE " +
                "$tableName " +
                "(${UserEntryColumns.columnUserId} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${UserEntryColumns.columnUserName} TEXT, " +
                "${UserEntryColumns.columnUserEmail} TEXT, " +
                "${UserEntryColumns.columnUserPassword} TEXT, " +
                "${UserEntryColumns.columnUserBirthday} TEXT, " +
                "${UserEntryColumns.columnUserGender} TEXT, " +
                "${UserEntryColumns.columnUserHeight} TEXT, " +
                "${UserEntryColumns.columnUserWeight} TEXT, " +
                "UNIQUE(${UserEntryColumns.columnUserId}, ${UserEntryColumns.columnUserEmail}) ON CONFLICT REPLACE);"

    }
}