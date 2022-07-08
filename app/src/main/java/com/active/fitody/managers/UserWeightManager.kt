package com.active.fitody.managers

import com.active.fitody.database.DatabaseQueryResultSingleRow
import com.active.fitody.model.UserWeightInfoDTO
import com.fiction.securetype.database.DBContract

class UserWeightManager {

    companion object {
        fun sharedInstance(): UserWeightManager {
            return UserWeightManager()
        }
    }

    fun addUserWeightEntry(userWeightInfo: UserWeightInfoDTO) {
        GenericDBManager.dbContext().insert(DBContract.UserWeightInfoTable.tableName, userWeightInfo.contentValues())
    }

    fun getUserWeightHistory(): ArrayList<DatabaseQueryResultSingleRow> {
        val query = "SELECT * FROM ${DBContract.UserWeightInfoTable.tableName}"
        return GenericDBManager.dbContext().select(query)
    }
}