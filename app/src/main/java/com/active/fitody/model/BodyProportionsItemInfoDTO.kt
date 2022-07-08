package com.active.fitody.model

import android.content.ContentValues
import com.active.fitody.UserPrefs
import com.active.fitody.database.DatabaseQueryResultSingleRow
import com.active.fitody.util.Enum
import com.fiction.securetype.database.DBContract

class BodyProportionsItemInfoDTO {
    constructor()

    constructor(partName: String, partValue: String, bodyPart: Enum.bodyPartTypeEnum, proportionType: Enum.bodyProportionsType) {
        this.bodyPartProportionItemName = partName
        this.bodyPartMeasurementValue = partValue
        this.bodyPartType = bodyPart
        this.bodyProportionType = proportionType
    }

    constructor(dbRow: DatabaseQueryResultSingleRow) {
        bodyPartProportionItemName = dbRow.stringValue(DBContract.BodyProportionRecordTableColumn.columnPartName)
        bodyPartMeasurementValue = dbRow.stringValue(DBContract.BodyProportionRecordTableColumn.columnMeasurement)
        bodyPartType = Enum.bodyPartTypeEnum.enumFromInt(dbRow.intValue(DBContract.BodyProportionRecordTableColumn.columnBodyPartType))
        bodyProportionType = Enum.bodyProportionsType.enumFromInt(dbRow.intValue(DBContract.BodyProportionRecordTableColumn.columnPropotionType))

    }

    var bodyPartProportionItemName: String = ""
    var bodyPartMeasurementValue: String = ""
    var bodyPartType: Enum.bodyPartTypeEnum? = null
    var bodyProportionType: Enum.bodyProportionsType? = null

    fun contentValues() : ContentValues {
        val contentValues = ContentValues()
        contentValues.put(DBContract.BodyProportionRecordTableColumn.columnUserId, UserPrefs.getInstance().getUserId())
        contentValues.put(DBContract.BodyProportionRecordTableColumn.columnPartName, bodyPartProportionItemName)
        contentValues.put(DBContract.BodyProportionRecordTableColumn.columnMeasurement, bodyPartMeasurementValue)
        contentValues.put(DBContract.BodyProportionRecordTableColumn.columnPropotionType, bodyProportionType?.value)
        contentValues.put(DBContract.BodyProportionRecordTableColumn.columnBodyPartType, bodyPartType?.value)
        return contentValues
    }
}