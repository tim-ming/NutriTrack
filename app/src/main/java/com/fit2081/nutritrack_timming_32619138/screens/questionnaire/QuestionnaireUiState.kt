package com.fit2081.nutritrack_timming_32619138.screens.questionnaire

import PersonaType
import android.util.Log
import com.fit2081.nutritrack_timming_32619138.data.foodIntake.FoodIntake


data class QuestionnaireUiState(
    val foodCategories: Set<String> = emptySet(),
    val persona: PersonaType? = null,
    val timings: List<Timing> = TimingQuestion.entries.map { Timing(it, DEFAULT_TIMING)}
) {
    val canSubmit: Boolean
        get() = persona != null

    /**
     * Takes FoodIntake (Entity) and converts (copies) it into a UiState.
     *
     * @param entity FoodIntake (Entity) to be converted (copied)
     */
    fun copyFromEntity(entity: FoodIntake?): QuestionnaireUiState {
        return if (entity == null) this else copy(
            foodCategories = buildSet {
                if (entity.fruits) add("Fruits")
                if (entity.vegetables) add("Vegetables")
                if (entity.grains) add("Grains")
                if (entity.redMeat) add("Red Meat")
                if (entity.seafood) add("Seafood")
                if (entity.poultry) add("Poultry")
                if (entity.fish) add("Fish")
                if (entity.eggs) add("Eggs")
                if (entity.nutsSeeds) add("Nuts/Seeds")
            },
            persona = entity.persona,
            timings = listOf(
                Timing(TimingQuestion.BIGGEST_MEAL, entity.biggestMealTime),
                Timing(TimingQuestion.SLEEP_TIME, entity.sleepTime),
                Timing(TimingQuestion.WAKE_UP_TIME, entity.wakeUpTime)
            )
        )
    }

    /**
     * Takes QuestionnaireUiState and converts (copies) it into a FoodIntake (Entity).
     *
     * @param userId User ID of the patient
     */
    fun toEntity(userId: String): FoodIntake {
        val biggestMealTime = timings.find{ it.type == TimingQuestion.BIGGEST_MEAL }?.time ?: DEFAULT_TIMING
        val sleepTime = timings.find{ it.type == TimingQuestion.SLEEP_TIME }?.time ?: DEFAULT_TIMING
        val wakeUpTime = timings.find{ it.type == TimingQuestion.WAKE_UP_TIME }?.time ?: DEFAULT_TIMING
        return FoodIntake(
            userId = userId,
            persona = persona!!,
            fruits = foodCategories.contains("Fruits"),
            vegetables = foodCategories.contains("Vegetables"),
            grains = foodCategories.contains("Grains"),
            redMeat = foodCategories.contains("Red Meat"),
            seafood = foodCategories.contains("Seafood"),
            poultry = foodCategories.contains("Poultry"),
            fish = foodCategories.contains("Fish"),
            eggs = foodCategories.contains("Eggs"),
            nutsSeeds = foodCategories.contains("Nuts/Seeds"),
            biggestMealTime = biggestMealTime,
            sleepTime = sleepTime,
            wakeUpTime = wakeUpTime
        )
    }
}