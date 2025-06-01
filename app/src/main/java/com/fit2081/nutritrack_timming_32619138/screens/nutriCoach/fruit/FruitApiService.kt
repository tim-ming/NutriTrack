package com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.fruit

import retrofit2.http.GET
import retrofit2.http.Path

interface FruitApiService {
    @GET("{name}")
    suspend fun getFruitByName(@Path("name") name: String): Fruit

    @GET("all")
    suspend fun getAllFruits(): List<Fruit>
}