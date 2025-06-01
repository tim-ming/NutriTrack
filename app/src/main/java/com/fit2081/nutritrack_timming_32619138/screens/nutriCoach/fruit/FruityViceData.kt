package com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.fruit

/**
 * Data class representing a Fruit with its properties.
 */
data class Fruit(
    val name: String,
    val id: Int,
    val family: String,
    val genus: String,
    val order: String,
    val nutritions: Nutritions
)

/**
 * Data class representing the nutritional information of a Fruit.
 * All values are per 100g in grams unless otherwise specified.
 */
data class Nutritions(
    val carbohydrates: Double,
    val protein: Double,
    val fat: Double,
    val calories: Int,
    val sugar: Double
)