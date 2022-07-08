package com.active.fitody.managers

import com.active.fitody.database.DatabaseQueryResultSingleRow
import com.active.fitody.model.UserDTO
import com.active.fitody.model.UserWeightInfoDTO
import com.fiction.securetype.database.DBContract

class UserManager {

    companion object {
        fun sharedInstance(): UserManager {
            return UserManager()
        }
    }

    fun addUserDetails(user: UserDTO) {
        GenericDBManager.dbContext().insert(DBContract.UserEntryTable.tableName, user.contentValues())
    }

    fun getUserInfo(email: String, userPassword: String): ArrayList<DatabaseQueryResultSingleRow> {
        val query = "SELECT * FROM ${DBContract.UserEntryTable.tableName} WHERE user_email = '$email' AND user_password = '$userPassword'"
        return GenericDBManager.dbContext().select(query)
    }

    fun getUserInfoFromEmail(email: String): ArrayList<DatabaseQueryResultSingleRow> {
        val query = "SELECT * FROM ${DBContract.UserEntryTable.tableName} WHERE user_email = '$email'"
        return GenericDBManager.dbContext().select(query)
    }

    fun getUserInfoFromUserId(userId: Int): ArrayList<DatabaseQueryResultSingleRow> {
        val query = "SELECT * FROM ${DBContract.UserEntryTable.tableName} WHERE user_id = $userId"
        return GenericDBManager.dbContext().select(query)
    }
}