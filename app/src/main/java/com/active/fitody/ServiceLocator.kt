package com.active.fitody

import com.active.fitody.services.BodyProportionsManagementService
import com.active.fitody.services.ExerciseManagementService
import com.active.fitody.services.UserManagementService
import com.active.fitody.services.WeightManagementService

object ServiceLocator {
    fun getWeightManagementService(): WeightManagementService = WeightManagementService()
    fun getExerciseManagementService(): ExerciseManagementService = ExerciseManagementService()
    fun getBodyProportionsManagementService(): BodyProportionsManagementService = BodyProportionsManagementService()
    fun getUserManagementService(): UserManagementService = UserManagementService()
}