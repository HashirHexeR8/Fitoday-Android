package com.active.fitody

import android.content.Context
import android.content.SharedPreferences
import com.active.fitody.util.Constants
import com.active.fitody.util.Enum

class UserPrefs {

    companion object {
        private var instance: UserPrefs? = null
        fun getInstance() : UserPrefs
        {
            if (instance == null) {
                instance = UserPrefs()
            }
            return instance!!
        }
    }

    private val rulerUnitKey = "RulerUnitKey"
    private val weightUnitKey = "WeightUnitKey"
    private val userIdKey = "userIdKey"
    private val initialWeightKey = "initialWeightKey"
    private val userWeightTargetKey = "userWeightTargetKey"
    private val userWeightKey = "userWeightKey"
    private val userWeightGoalTypeKey = "userWeightGoalTypeKey"
    private val userHeightKey = "userHeightKey"
    private val userHeightUnitKey = "userHeightUnitKey"
    private val userLoginStatusKey = "userLoginStatusKey"


    private fun putString(key: String, value: String) {
        val sharedPreference = FitodyApplication.context().getSharedPreferences(Constants.sharedPreferenceName, Context.MODE_PRIVATE)
        val preferenceEditor = sharedPreference.edit()
        preferenceEditor.putString(key, value)
        preferenceEditor.commit()
    }

    private fun getString(key: String, defaultValue: String): String {
        val sharedPreference = FitodyApplication.context().getSharedPreferences(Constants.sharedPreferenceName, Context.MODE_PRIVATE)
        return sharedPreference.getString(key, defaultValue) ?: defaultValue
    }

    private fun putInt(key: String, value: Int) {
        val sharedPreference = FitodyApplication.context().getSharedPreferences(Constants.sharedPreferenceName, Context.MODE_PRIVATE)
        val preferenceEditor = sharedPreference.edit()
        preferenceEditor.putInt(key, value)
        preferenceEditor.commit()
    }

    private fun getInt(key: String, defaultValue: Int): Int {
        val sharedPreference = FitodyApplication.context().getSharedPreferences(Constants.sharedPreferenceName, Context.MODE_PRIVATE)
        return sharedPreference.getInt(key, defaultValue) ?: defaultValue
    }

    private fun putFloat(key: String, value: Float) {
        val sharedPreference = FitodyApplication.context().getSharedPreferences(Constants.sharedPreferenceName, Context.MODE_PRIVATE)
        val preferenceEditor = sharedPreference.edit()
        preferenceEditor.putFloat(key, value)
        preferenceEditor.commit()
    }

    private fun getFloat(key: String, defaultValue: Float): Float {
        val sharedPreference = FitodyApplication.context().getSharedPreferences(Constants.sharedPreferenceName, Context.MODE_PRIVATE)
        return sharedPreference.getFloat(key, defaultValue) ?: defaultValue
    }

    private fun putBoolean(key: String, value: Boolean) {
        val sharedPreference = FitodyApplication.context().getSharedPreferences(Constants.sharedPreferenceName, Context.MODE_PRIVATE)
        val preferenceEditor = sharedPreference.edit()
        preferenceEditor.putBoolean(key, value)
        preferenceEditor.commit()
    }

    private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        val sharedPreference = FitodyApplication.context().getSharedPreferences(Constants.sharedPreferenceName, Context.MODE_PRIVATE)
        return sharedPreference.getBoolean(key, defaultValue) ?: defaultValue
    }

    fun putUserId(userId: Int) {
        putInt(userIdKey, userId)
    }

    fun getUserId(): Int {
        return getInt(userIdKey, 0)
    }

    fun putUserTapeUnit(unit: String) {
        putString(rulerUnitKey, unit)
    }

    fun getUserTapeUnit(): Enum.userRularUnit {
        return Enum.userRularUnit.enumFromString(getString(rulerUnitKey, "inch"))
    }

    fun putUserWeightUnit(unit: String) {
        putString(weightUnitKey, unit)
    }

    fun getUserWeightUnit(): Enum.userWeightUnit {
        return Enum.userWeightUnit.enumFromString(getString(rulerUnitKey, "inch"))
    }

    fun putUserCurrentWeight(unit: Float) {
        putFloat(userWeightKey, unit)
    }

    fun getUserCurrentWeight(): Float {
        return getFloat(userWeightKey, 0F)
    }

    fun putUserWeightTarget(unit: Float) {
        putFloat(userWeightTargetKey, unit)
    }

    fun getUserWeightTarget(): Float {
        return getFloat(userWeightTargetKey, 0F)
    }

    fun putUserGoalType(type: Int) {
        putInt(userWeightGoalTypeKey, type)
    }

    fun putUserInitialWeight(weight: Float) {
        putFloat(initialWeightKey, weight)
    }

    fun getUserInitialWeight(): Float {
        return getFloat(initialWeightKey, 0f)
    }

    fun getUserWeightGoalType(): Enum.weightGoalType {
        return Enum.weightGoalType.enumFromString(getInt(userWeightGoalTypeKey, 0))
    }

    fun putUserHeight(height: Float) {
        putFloat(userHeightKey, height)
    }

    fun getUserHeight(): Float {
        return getFloat(userHeightKey, 0F)
    }

    fun getUserHeightUnit(): Enum.userRularUnit {
        return Enum.userRularUnit.enumFromString(getString(userHeightUnitKey, "feet"))
    }

    fun putUserHeightUnit(unit: String) {
        putString(userHeightUnitKey, unit)
    }

    fun putUserLoggedIn(isLogin: Boolean) {
        putBoolean(userLoginStatusKey, isLogin)
    }

    fun isUserLoggedIn(): Boolean {
        return getBoolean(userLoginStatusKey, false)
    }
}