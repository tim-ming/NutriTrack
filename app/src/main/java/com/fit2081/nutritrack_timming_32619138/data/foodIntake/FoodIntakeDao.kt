package com.fit2081.nutritrack_timming_32619138.data.foodIntake

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface FoodIntakeDao {
    @Upsert
    suspend fun updateResponse(response: FoodIntake)

    @Query("SELECT * FROM food_intake WHERE userId = :userId")
    suspend fun getResponseForPatient(userId: String): FoodIntake?
}