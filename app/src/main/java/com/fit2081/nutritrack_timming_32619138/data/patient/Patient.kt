package com.fit2081.nutritrack_timming_32619138.data.patient
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fit2081.nutritrack_timming_32619138.data.user.User

@Entity(
    tableName = "patients",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)
data class Patient(
    @PrimaryKey
    val userId: String,
    val phoneNumber: String,
    val name: String = "",
    val sex: String,

    // Total HEIFA scores
    val heifaTotalScore: Float,
    val discretionaryHeifaScore: Float,
    val vegetablesHeifaScore: Float,
    val fruitHeifaScore: Float,
    val grainsAndCerealsHeifaScore: Float,
    val wholeGrainsHeifaScore: Float,
    val meatAndAlternativesHeifaScore: Float,
    val dairyAndAlternativesHeifaScore: Float,
    val sodiumHeifaScore: Float,
    val alcoholHeifaScore: Float,
    val waterHeifaScore: Float,
    val sugarHeifaScore: Float,
    val saturatedFatHeifaScore: Float,
    val unsaturatedFatHeifaScore: Float,

    // Discretionary scores
    val discretionaryServeSize: Float,

    // Vegetables scores
    val vegetablesWithLegumesAllocatedServeSize: Float,
    val legumesAllocatedVegetables: Float,
    val vegetablesVariationsScore: Float,
    val vegetablesCruciferous: Float,
    val vegetablesTubeAndBulb: Float,
    val vegetablesOther: Float,
    val legumes: Float,
    val vegetablesGreen: Float,
    val vegetablesRedAndOrange: Float,

    // Fruit scores
    val fruitServeSize: Float,
    val fruitVariationsScore: Float,
    val fruitPome: Float,
    val fruitTropicalAndSubtropical: Float,
    val fruitBerry: Float,
    val fruitStone: Float,
    val fruitCitrus: Float,
    val fruitOther: Float,

    // Grains scores
    val grainsAndCerealsServeSize: Float,
    val grainsAndCerealsNonWholeGrains: Float,

    // Whole grains
    val wholeGrainsServeSize: Float,

    // Meat scores
    val meatAndAlternativesWithLegumesAllocatedServeSize: Float,
    val legumesAllocatedMeatAndAlternatives: Float,

    // Dairy scores
    val dairyAndAlternativesServeSize: Float,

    // Sodium scores
    val sodiumMgMilligrams: Float,

    // Alcohol scores
    val alcoholStandardDrinks: Float,

    // Water scores
    val water: Float,
    val waterTotalMl: Float,
    val beverageTotalMl: Float,

    // Sugar scores
    val sugar: Float,

    // Fat scores
    val saturatedFat: Float,
    val unsaturatedFatServeSize: Float,
){
    companion object{
        val MOCK = Patient(
            userId = "patient001",
            phoneNumber = "123-456-7890",
            name = "John Doe",
            sex = "Male",

            heifaTotalScore = 65.0f,
            discretionaryHeifaScore = 4.0f,
            vegetablesHeifaScore = 7.0f,
            fruitHeifaScore = 6.0f,
            grainsAndCerealsHeifaScore = 5.5f,
            wholeGrainsHeifaScore = 4.0f,
            meatAndAlternativesHeifaScore = 6.0f,
            dairyAndAlternativesHeifaScore = 5.0f,
            sodiumHeifaScore = 3.0f,
            alcoholHeifaScore = 2.5f,
            waterHeifaScore = 6.0f,
            sugarHeifaScore = 3.5f,
            saturatedFatHeifaScore = 3.0f,
            unsaturatedFatHeifaScore = 4.5f,

            discretionaryServeSize = 2.0f,

            vegetablesWithLegumesAllocatedServeSize = 3.0f,
            legumesAllocatedVegetables = 0.5f,
            vegetablesVariationsScore = 4.5f,
            vegetablesCruciferous = 0.7f,
            vegetablesTubeAndBulb = 0.6f,
            vegetablesOther = 0.8f,
            legumes = 0.5f,
            vegetablesGreen = 0.7f,
            vegetablesRedAndOrange = 0.7f,

            fruitServeSize = 2.0f,
            fruitVariationsScore = 3.5f,
            fruitPome = 0.6f,
            fruitTropicalAndSubtropical = 0.4f,
            fruitBerry = 0.3f,
            fruitStone = 0.2f,
            fruitCitrus = 0.3f,
            fruitOther = 0.2f,

            grainsAndCerealsServeSize = 4.0f,
            grainsAndCerealsNonWholeGrains = 2.0f,

            wholeGrainsServeSize = 2.0f,

            meatAndAlternativesWithLegumesAllocatedServeSize = 3.5f,
            legumesAllocatedMeatAndAlternatives = 0.5f,

            dairyAndAlternativesServeSize = 2.5f,

            sodiumMgMilligrams = 2100.0f,

            alcoholStandardDrinks = 1.5f,

            water = 6.0f,
            waterTotalMl = 1500.0f,
            beverageTotalMl = 1800.0f,

            sugar = 25.0f,

            saturatedFat = 18.0f,
            unsaturatedFatServeSize = 3.0f
        )

        val UNKNOWN = Patient(
            userId = "0",
            phoneNumber = "1234567890",
            name = "Unknown",
            sex = "Male",

            heifaTotalScore = 0.0f,
            discretionaryHeifaScore = 0.0f,
            vegetablesHeifaScore = 0.0f,
            fruitHeifaScore = 0.0f,
            grainsAndCerealsHeifaScore = 0.0f,
            wholeGrainsHeifaScore = 0.0f,
            meatAndAlternativesHeifaScore = 0.0f,
            dairyAndAlternativesHeifaScore = 0.0f,
            sodiumHeifaScore = 0.0f,
            alcoholHeifaScore = 0.0f,
            waterHeifaScore = 0.0f,
            sugarHeifaScore = 0.0f,
            saturatedFatHeifaScore = 0.0f,
            unsaturatedFatHeifaScore = 0.0f,

            discretionaryServeSize = 0.0f,

            vegetablesWithLegumesAllocatedServeSize = 0.0f,
            legumesAllocatedVegetables = 0.0f,
            vegetablesVariationsScore = 0.0f,
            vegetablesCruciferous = 0.0f,
            vegetablesTubeAndBulb = 0.0f,
            vegetablesOther = 0.0f,
            legumes = 0.0f,
            vegetablesGreen = 0.0f,
            vegetablesRedAndOrange = 0.0f,

            fruitServeSize = 0.0f,
            fruitVariationsScore = 0.0f,
            fruitPome = 0.0f,
            fruitTropicalAndSubtropical = 0.0f,
            fruitBerry = 0.0f,
            fruitStone = 0.0f,
            fruitCitrus = 0.0f,
            fruitOther = 0.0f,

            grainsAndCerealsServeSize = 0.0f,
            grainsAndCerealsNonWholeGrains = 0.0f,

            wholeGrainsServeSize = 0.0f,

            meatAndAlternativesWithLegumesAllocatedServeSize = 0.0f,
            legumesAllocatedMeatAndAlternatives = 0.0f,

            dairyAndAlternativesServeSize = 0.0f,

            sodiumMgMilligrams = 0.0f,

            alcoholStandardDrinks = 0.0f,

            water = 0.0f,
            waterTotalMl = 0.0f,
            beverageTotalMl = 0.0f,

            sugar = 0.0f,

            saturatedFat = 0.0f,
            unsaturatedFatServeSize = 0.0f,
        )
    }

    fun toHumanString(): String {
        return """
        |HEIFA Nutritional Assessment
        |---------------------------
        |Basic Information:
        |  Sex: $sex
        |  HEIFA Total Score: $heifaTotalScore
        |
        |Component Scores:
        |  Discretionary Foods: $discretionaryHeifaScore
        |  Vegetables: $vegetablesHeifaScore
        |  Fruit: $fruitHeifaScore
        |  Grains & Cereals: $grainsAndCerealsHeifaScore
        |  Whole Grains: $wholeGrainsHeifaScore
        |  Meat & Alternatives: $meatAndAlternativesHeifaScore
        |  Dairy & Alternatives: $dairyAndAlternativesHeifaScore
        |  Sodium: $sodiumHeifaScore
        |  Alcohol: $alcoholHeifaScore
        |  Water: $waterHeifaScore
        |  Sugar: $sugarHeifaScore
        |  Saturated Fat: $saturatedFatHeifaScore
        |  Unsaturated Fat: $unsaturatedFatHeifaScore
        |
        |Serving Sizes:
        |  Discretionary Foods: $discretionaryServeSize
        |  Vegetables (with legumes): $vegetablesWithLegumesAllocatedServeSize
        |  Fruit: $fruitServeSize
        |  Grains & Cereals: $grainsAndCerealsServeSize
        |  Whole Grains: $wholeGrainsServeSize
        |  Meat & Alternatives (with legumes): $meatAndAlternativesWithLegumesAllocatedServeSize
        |  Dairy & Alternatives: $dairyAndAlternativesServeSize
        |  Unsaturated Fat: $unsaturatedFatServeSize
        |
        |Legumes:
        |  Total Legumes: $legumes
        |  Allocated to Vegetables: $legumesAllocatedVegetables
        |  Allocated to Meat & Alternatives: $legumesAllocatedMeatAndAlternatives
        |
        |Vegetables Detail:
        |  Variation Score: $vegetablesVariationsScore
        |  Cruciferous: $vegetablesCruciferous
        |  Tube & Bulb: $vegetablesTubeAndBulb
        |  Green: $vegetablesGreen
        |  Red & Orange: $vegetablesRedAndOrange
        |  Other: $vegetablesOther
        |
        |Fruit Detail:
        |  Variation Score: $fruitVariationsScore
        |  Pome (e.g., apples, pears): $fruitPome
        |  Tropical & Subtropical: $fruitTropicalAndSubtropical
        |  Berries: $fruitBerry
        |  Stone Fruits: $fruitStone
        |  Citrus: $fruitCitrus
        |  Other: $fruitOther
        |
        |Grains Detail:
        |  Non-Whole Grains: $grainsAndCerealsNonWholeGrains
        |
        |Other Nutritional Metrics:
        |  Sodium: $sodiumMgMilligrams mg
        |  Alcohol: $alcoholStandardDrinks standard drinks
        |  Water: $water
        |  Total Water: $waterTotalMl ml
        |  Total Beverages: $beverageTotalMl ml
        |  Sugar: $sugar
        |  Saturated Fat: $saturatedFat
    """.trimMargin()
    }
}