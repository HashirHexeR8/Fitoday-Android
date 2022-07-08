package com.active.fitody.managers

import com.active.fitody.database.DatabaseQueryResultSingleRow
import com.active.fitody.model.BodyProportionsItemInfoDTO
import com.active.fitody.model.SkipRopeActivityDataDTO
import com.fiction.securetype.database.DBContract

class BodyProportionsManager {

    companion object {
        fun sharedInstance(): BodyProportionsManager {
            return BodyProportionsManager()
        }
    }

    fun persistBodyProportionMeasurements(bodyPropotionItemDTO: BodyProportionsItemInfoDTO) {
        GenericDBManager.dbContext().insertOrUpdate(DBContract.BodyProportionRecordTable.tableName, bodyPropotionItemDTO.contentValues())
    }

    fun getProportionMeasurements(): ArrayList<DatabaseQueryResultSingleRow> {
        val query = "SELECT * FROM ${DBContract.BodyProportionRecordTable.tableName}"
        return GenericDBManager.dbContext().select(query)
    }
}