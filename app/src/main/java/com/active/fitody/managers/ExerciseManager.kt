package com.active.fitody.managers

import com.active.fitody.database.DatabaseQueryResultSingleRow
import com.active.fitody.model.SkipRopeActivityDataDTO
import com.fiction.securetype.database.DBContract

class ExerciseManager {

    companion object {
        fun sharedInstance(): ExerciseManager {
            return ExerciseManager()
        }
    }

    fun persistSkipRopeActivityData(skipRopeActivityData: SkipRopeActivityDataDTO) {
        GenericDBManager.dbContext().insert(DBContract.SkipRopeActivityRecordTable.tableName, skipRopeActivityData.contentValues())
    }

    fun getSkipRopeActivityData(): ArrayList<DatabaseQueryResultSingleRow> {
        val query = "SELECT * FROM ${DBContract.SkipRopeActivityRecordTable.tableName}"
        return GenericDBManager.dbContext().select(query)
    }
}