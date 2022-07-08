package com.active.fitody.services

import com.active.fitody.managers.ExerciseManager
import com.active.fitody.managers.GenericDBManager
import com.active.fitody.model.SkipRopeActivityDataDTO
import com.active.fitody.model.SkipRopeHistoryDTO
import com.fiction.securetype.database.DBContract

class ExerciseManagementService {

    fun persistSkipRopeActivityData(skipRopeActivityData: SkipRopeActivityDataDTO) {
        ExerciseManager.sharedInstance().persistSkipRopeActivityData(skipRopeActivityData)
    }

    fun getSkipRopeActivityData(): SkipRopeHistoryDTO {
        var totalSkips = 0
        var totalCaloriesBurned = 0.0
        var totalTimeElapsed = 0
        var skipCount = 0
        val dbRows = ExerciseManager.sharedInstance().getSkipRopeActivityData()
        dbRows.forEach { dbRow ->
            totalSkips += dbRow.intValue(DBContract.SkipRopeActivityRecordTableColumn.columnTotalSkips)
            totalCaloriesBurned += dbRow.doubleValue(DBContract.SkipRopeActivityRecordTableColumn.columnCaloriesBurned)
            totalTimeElapsed += dbRow.intValue(DBContract.SkipRopeActivityRecordTableColumn.columnTimeElapsed)
            skipCount++
        }
        var skipHistoryData = SkipRopeHistoryDTO()
        if (skipCount > 0) {
            skipHistoryData.averageSkips = totalSkips/skipCount
            skipHistoryData.totalCaloriesBurned = totalCaloriesBurned
            skipHistoryData.totalTimeElapsed = totalTimeElapsed
            skipHistoryData.totalSkips = totalSkips
        }
        return skipHistoryData
    }
}