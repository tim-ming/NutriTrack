package com.fit2081.nutritrack_timming_32619138.data.nutriCoachTips

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface NutriCoachTipDao {
    @Insert
    suspend fun insert(entry: NutriCoachTip)

    @Query("SELECT * FROM nutriCoachTips WHERE userId = :userId")
    suspend fun getAllTipsByUserId(userId: String): List<NutriCoachTip>
}