package com.active.fitody.services

import com.active.fitody.UserPrefs
import com.active.fitody.database.DatabaseQueryResultSingleRow
import com.active.fitody.managers.UserManager
import com.active.fitody.model.UserDTO

class UserManagementService {

    fun persistUserData(user: UserDTO) {
        UserManager.sharedInstance().addUserDetails(user)
        val userInfo = getUserDetails(user.userEmail, user.userPassword)
        UserPrefs.getInstance().putUserId(userInfo.userId)
    }

    fun getUserDetails(userEmail: String, userPassword: String): UserDTO {
        return UserDTO(UserManager.sharedInstance().getUserInfo(userEmail, userPassword).firstOrNull() ?: DatabaseQueryResultSingleRow())
    }

    fun getLoggedInUserInfo(): UserDTO {
        return UserDTO(UserManager.sharedInstance().getUserInfoFromUserId(UserPrefs.getInstance().getUserId()).firstOrNull() ?: DatabaseQueryResultSingleRow())
    }

    fun getUserInfo(userEmail: String, userPassword: String): Boolean {
        val userInfo = getUserDetails(userEmail, userPassword)
        UserPrefs.getInstance().putUserId(userInfo.userId)
        return UserManager.sharedInstance().getUserInfo(userEmail, userPassword).isNotEmpty()
    }

    fun getUserExistStatus(userEmail: String): Boolean {
        return UserManager.sharedInstance().getUserInfoFromEmail(userEmail).isNotEmpty()
    }
}