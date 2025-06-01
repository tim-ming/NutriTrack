package com.fit2081.nutritrack_timming_32619138.data.foodIntake
import PersonaType
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import com.fit2081.nutritrack_timming_32619138.data.patient.Patient
import java.time.LocalDateTime

@Entity(
    tableName = "food_intake",
    foreignKeys = [
        ForeignKey(
            entity = Patient::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FoodIntake(
    @PrimaryKey
    val userId: String,
    val persona: PersonaType,

    val fruits: Boolean = false,
    val vegetables: Boolean = false,
    val grains: Boolean = false,
    val redMeat: Boolean = false,
    val seafood: Boolean = false,
    val poultry: Boolean = false,
    val fish: Boolean = false,
    val eggs: Boolean = false,
    val nutsSeeds: Boolean = false,

    val biggestMealTime: LocalDateTime,
    val sleepTime: LocalDateTime,
    val wakeUpTime: LocalDateTime
)