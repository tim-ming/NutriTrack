package com.fit2081.nutritrack_timming_32619138.data.patient

data class PatientStats(
    val heifaTotalScore: StatEntry,
    val discretionaryHeifaScore: StatEntry,
    val vegetablesHeifaScore: StatEntry,
    val fruitHeifaScore: StatEntry,
    val grainsAndCerealsHeifaScore: StatEntry,
    val wholeGrainsHeifaScore: StatEntry,
    val meatAndAlternativesHeifaScore: StatEntry,
    val dairyAndAlternativesHeifaScore: StatEntry,
    val sodiumHeifaScore: StatEntry,
    val alcoholHeifaScore: StatEntry,
    val waterHeifaScore: StatEntry,
    val sugarHeifaScore: StatEntry,
    val saturatedFatHeifaScore: StatEntry,
    val unsaturatedFatHeifaScore: StatEntry,
) {
    fun getCoreFoodStats(): List<StatEntry> = listOf(
        vegetablesHeifaScore,
        fruitHeifaScore,
        grainsAndCerealsHeifaScore,
        wholeGrainsHeifaScore,
        meatAndAlternativesHeifaScore,
        dairyAndAlternativesHeifaScore
    )

    fun getLimitStats(): List<StatEntry> = listOf(
        discretionaryHeifaScore,
        alcoholHeifaScore,
        sodiumHeifaScore,
        sugarHeifaScore,
        saturatedFatHeifaScore
    )

    fun getOtherStats(): List<StatEntry> = listOf(
        waterHeifaScore,
        unsaturatedFatHeifaScore
    )

    private fun calculateAverageScore(stats: List<StatEntry>): Float {
        if (stats.isEmpty()) {
            return 0.0f
        }
        return stats.sumOf { it.score.toDouble() }.toFloat() / stats.size
    }

    /**
     * Calculates and returns a map of average scores for each defined subcategory.
     * The keys of the map are descriptive names for the subcategories.
     */
    fun getSubcategoryAverages(): List<StatEntry> {
        val averages = mutableListOf<StatEntry>()

        val coreFoodStats = getCoreFoodStats()
        if (coreFoodStats.isNotEmpty()) {
            averages.add(StatEntry("Core Foods",calculateAverageScore(coreFoodStats)))
        } else {
            averages.add(StatEntry("Core Foods",0.0f))
        }

        val limitStats = getLimitStats()
        if (limitStats.isNotEmpty()) {
            averages.add(StatEntry("Discretionary",calculateAverageScore(limitStats)))
        } else {
            averages.add(StatEntry("Discretionary",0.0f))
        }

        val otherStats = getOtherStats()
        if (otherStats.isNotEmpty()) {
            averages.add(StatEntry("Others",calculateAverageScore(otherStats)))
        } else {
            averages.add(StatEntry("Others",0.0f))
        }

        return averages
    }

    /**
     * Returns a list of all the stats, excluding total score, in ascending order.
     */
    fun getAllNonTotalStats(): List<StatEntry> {
        return getCoreFoodStats() + getLimitStats() + getOtherStats()
    }

    companion object {
        fun fromPatient(patient: Patient): PatientStats {
            return PatientStats(
                heifaTotalScore = StatEntry("Total score", patient.heifaTotalScore),
                discretionaryHeifaScore = StatEntry(
                    "Discretionary",
                    patient.discretionaryHeifaScore
                ),
                vegetablesHeifaScore = StatEntry("Vegetables", patient.vegetablesHeifaScore),
                fruitHeifaScore = StatEntry("Fruit", patient.fruitHeifaScore),
                grainsAndCerealsHeifaScore = StatEntry(
                    "Grains and cereals",
                    patient.grainsAndCerealsHeifaScore
                ),
                wholeGrainsHeifaScore = StatEntry("Whole grains", patient.wholeGrainsHeifaScore),
                meatAndAlternativesHeifaScore = StatEntry(
                    "Meat and alternatives",
                    patient.meatAndAlternativesHeifaScore
                ),
                dairyAndAlternativesHeifaScore = StatEntry(
                    "Dairy and alternatives",
                    patient.dairyAndAlternativesHeifaScore
                ),
                sodiumHeifaScore = StatEntry("Sodium", patient.sodiumHeifaScore),
                alcoholHeifaScore = StatEntry("Alcohol", patient.alcoholHeifaScore),
                waterHeifaScore = StatEntry("Water", patient.waterHeifaScore),
                sugarHeifaScore = StatEntry("Sugar", patient.sugarHeifaScore),
                saturatedFatHeifaScore = StatEntry("Saturated fat", patient.saturatedFatHeifaScore),
                unsaturatedFatHeifaScore = StatEntry(
                    "Unsaturated fat",
                    patient.unsaturatedFatHeifaScore
                )
            )
        }

        val MOCK = fromPatient(Patient.MOCK)

        val UNKNOWN = fromPatient(Patient.UNKNOWN)
    }
}

data class StatEntry(val name: String, val score: Float)