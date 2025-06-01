package com.fit2081.nutritrack_timming_32619138.data.foodIntake

import android.content.Context
import com.fit2081.nutritrack_timming_32619138.data.AppDatabase

class FoodIntakeRepository(context: Context) {

    private val foodIntakeDao: FoodIntakeDao = AppDatabase.getDatabase(context).foodIntakeDao()
    suspend fun updateResponse(response: FoodIntake) = foodIntakeDao.updateResponse(response)
    suspend fun getResponseForPatient(userId: String): FoodIntake? =
        foodIntakeDao.getResponseForPatient(userId)
    suspend fun hasResponded(userId: String): Boolean =
        foodIntakeDao.getResponseForPatient(userId) != null

}