package com.active.fitody.model

import android.content.ContentValues
import com.active.fitody.database.DatabaseQueryResultSingleRow
import com.fiction.securetype.database.DBContract

class UserDTO {

    constructor()

    constructor(dbRow: DatabaseQueryResultSingleRow) {
        userId = dbRow.intValue(DBContract.UserEntryColumns.columnUserId)
        userName = dbRow.stringValue(DBContract.UserEntryColumns.columnUserName)
        userEmail = dbRow.stringValue(DBContract.UserEntryColumns.columnUserEmail)
        userPassword = dbRow.stringValue(DBContract.UserEntryColumns.columnUserPassword)
        userBirthday = dbRow.stringValue(DBContract.UserEntryColumns.columnUserBirthday)
        userGender = dbRow.stringValue(DBContract.UserEntryColumns.columnUserGender)
        userHeight = dbRow.stringValue(DBContract.UserEntryColumns.columnUserHeight)
        userWeight = dbRow.stringValue(DBContract.UserEntryColumns.columnUserWeight)
    }

    var userId: Int = 0
    var userName: String = ""
    var userEmail: String = ""
    var userPassword: String = ""
    var userBirthday: String = ""
    var userGender: String = ""
    var userHeight: String = ""
    var userWeight: String = ""

    fun contentValues(): ContentValues {
        var contentValues = ContentValues()
        contentValues.put(DBContract.UserEntryColumns.columnUserName, userName)
        contentValues.put(DBContract.UserEntryColumns.columnUserEmail, userEmail)
        contentValues.put(DBContract.UserEntryColumns.columnUserPassword, userPassword)
        contentValues.put(DBContract.UserEntryColumns.columnUserBirthday, userBirthday)
        contentValues.put(DBContract.UserEntryColumns.columnUserGender, userGender)
        contentValues.put(DBContract.UserEntryColumns.columnUserHeight, userHeight)
        contentValues.put(DBContract.UserEntryColumns.columnUserWeight, userWeight)
        return contentValues
    }

}