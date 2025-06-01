package com.fit2081.nutritrack_timming_32619138.data.nutriCoachTips
import PersonaType
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import com.fit2081.nutritrack_timming_32619138.data.patient.Patient
import java.time.LocalDateTime

@Entity(
    tableName = "nutriCoachTips",
    foreignKeys = [
        ForeignKey(
            entity = Patient::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NutriCoachTip(
    @PrimaryKey(autoGenerate = true)
    val tipId: Int = 0,
    val userId: String,
    val time: LocalDateTime,
    val message: String,
    val prompt: String,
)