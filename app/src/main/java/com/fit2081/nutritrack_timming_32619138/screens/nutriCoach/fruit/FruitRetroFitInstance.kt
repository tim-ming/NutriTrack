package com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.fruit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FruitRetrofitInstance {
    val api: FruitApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.fruityvice.com/api/fruit/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FruitApiService::class.java)
    }
}