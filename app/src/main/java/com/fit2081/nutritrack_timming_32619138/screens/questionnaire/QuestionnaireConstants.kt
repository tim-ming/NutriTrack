package com.fit2081.nutritrack_timming_32619138.screens.questionnaire

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

val FOOD_CATEGORIES = listOf(
    "Fruits",
    "Vegetables",
    "Grains",
    "Red Meat",
    "Seafood",
    "Poultry",
    "Fish",
    "Eggs",
    "Nuts/Seeds"
)


enum class TimingQuestion(val question: String) {
    BIGGEST_MEAL("What time of day approx. do you normally eat your biggest meal?"),
    SLEEP_TIME("What time of day approx. do you go to sleep at night?"),
    WAKE_UP_TIME("What time of day approx. do you wake up in the morning?")
}

val DEFAULT_TIMING: LocalDateTime = LocalDateTime.of(LocalDate.of(2025, 1, 23), LocalTime.of(0,0))

data class Timing(
    val type: TimingQuestion,
    val time: LocalDateTime = DEFAULT_TIMING
)
