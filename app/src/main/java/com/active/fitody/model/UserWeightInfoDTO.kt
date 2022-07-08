package com.active.fitody.model

import android.content.ContentValues
import com.active.fitody.database.DatabaseQueryResultSingleRow
import com.active.fitody.util.Utilities
import com.fiction.securetype.database.DBContract

class UserWeightInfoDTO {
    var userId: Int = 0
    var userWeightKg: Double = 0.0
    var userWeightLb: Double = 0.0
    var weightMeasurementDate: Int = 0
    var dayOfWeek: String = ""

    constructor()

    constructor(databaseRow: DatabaseQueryResultSingleRow) {
        userId = databaseRow.intValue(DBContract.UserWeightInfoTableColumn.columnUserId)
        userWeightKg = databaseRow.doubleValue(DBContract.UserWeightInfoTableColumn.columnUserWeightKg)
        userWeightLb = databaseRow.doubleValue(DBContract.UserWeightInfoTableColumn.columnUserWeightLb)
        weightMeasurementDate = databaseRow.intValue(DBContract.UserWeightInfoTableColumn.columnWeightMeasureDate)
        dayOfWeek = databaseRow.stringValue(DBContract.UserWeightInfoTableColumn.columnWeightDay)
    }

    fun contentValues(): ContentValues {
        val contentValues = ContentValues()

        contentValues.put(DBContract.UserWeightInfoTableColumn.columnUserId, userId)
        contentValues.put(DBContract.UserWeightInfoTableColumn.columnUserWeightKg, userWeightKg)
        contentValues.put(DBContract.UserWeightInfoTableColumn.columnUserWeightLb, userWeightLb)
        contentValues.put(DBContract.UserWeightInfoTableColumn.columnWeightMeasureDate, weightMeasurementDate)
        contentValues.put(DBContract.UserWeightInfoTableColumn.columnWeightDay, dayOfWeek)

        return contentValues
    }
}