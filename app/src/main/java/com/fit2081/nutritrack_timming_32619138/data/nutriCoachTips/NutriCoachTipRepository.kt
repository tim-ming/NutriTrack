package com.fit2081.nutritrack_timming_32619138.data.nutriCoachTips

import android.content.Context
import com.fit2081.nutritrack_timming_32619138.data.AppDatabase

class NutriCoachTipRepository(context: Context) {
    private val nutriCoachTipDao: NutriCoachTipDao = AppDatabase.getDatabase(context).nutriCoachTipDao()

    suspend fun insert(entry: NutriCoachTip) = nutriCoachTipDao.insert(entry)

    suspend fun getAllTipsByUserId(userId: String): List<NutriCoachTip> =
        nutriCoachTipDao.getAllTipsByUserId(userId)
}