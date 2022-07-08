package com.active.fitody.model

import android.content.ContentValues
import com.active.fitody.util.Enum
import com.fiction.securetype.database.DBContract

class SkipRopeActivityDataDTO {
    var userId: Int = 0
    var totalSkips: Int = 0
    var skipMode: Enum.skipRopeActivityMode = Enum.skipRopeActivityMode.freeJumpMode
    var timeElapsed: Int = 0
    var caloriesBurned: Double = 0.0
    var skippingActivityDate: String = ""

    fun contentValues(): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(DBContract.SkipRopeActivityRecordTableColumn.columnUserId, userId)
        contentValues.put(DBContract.SkipRopeActivityRecordTableColumn.columnTotalSkips, totalSkips)
        contentValues.put(DBContract.SkipRopeActivityRecordTableColumn.columnSkipMode, skipMode.name)
        contentValues.put(DBContract.SkipRopeActivityRecordTableColumn.columnTimeElapsed, timeElapsed)
        contentValues.put(DBContract.SkipRopeActivityRecordTableColumn.columnCaloriesBurned, caloriesBurned)
        contentValues.put(DBContract.SkipRopeActivityRecordTableColumn.columnSkipActivityDate, skippingActivityDate)

        return contentValues
    }
}