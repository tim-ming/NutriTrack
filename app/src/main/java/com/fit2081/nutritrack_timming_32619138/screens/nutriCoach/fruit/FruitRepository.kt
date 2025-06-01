package com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.fruit

class FruitRepository {
    suspend fun getFruitByName(name: String): Fruit {
        return FruitRetrofitInstance.api.getFruitByName(name)
    }

    suspend fun getAllFruits(): List<Fruit> {
        val allFruitsFromApi = FruitRetrofitInstance.api.getAllFruits()

        // Filter because fruityvice search API is faulty for fruits with more than one word
        return allFruitsFromApi.filter { fruit ->
            val name = fruit.name.trim() // Trim whitespace first

            // Name must not be empty and must not contain spaces
            val noSpaces = name.isNotEmpty() && !name.contains(' ')

            if (!noSpaces) {
                false // Filter out if it has spaces or is empty
            } else {
                // Count uppercase letters. Allow 0 or 1.
                val uppercaseCount = name.count { it.isUpperCase() }
                uppercaseCount <= 1
            }
        }
    }
}