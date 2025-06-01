package com.fit2081.nutritrack_timming_32619138.screens.insights

import com.fit2081.nutritrack_timming_32619138.data.patient.PatientStats

data class InsightsUiState(
    val stats: PatientStats = PatientStats.UNKNOWN,
    val isExpanded: Boolean = false
)