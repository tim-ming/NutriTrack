package com.fit2081.nutritrack_timming_32619138.screens.clinician.dashboard

import com.fit2081.nutritrack_timming_32619138.state.LoadingState

data class ClinicianDashboardUiState(
    val avgMaleScore: Float = 0.0f,
    val avgFemaleScore: Float = 0.0f,
    val genAiInsights: List<Pair<String,String>> = emptyList(),
    val cardLoadingState: LoadingState = LoadingState.Loading,
    val insightsLoadingState: LoadingState = LoadingState.Loading,
    val insightsNote: String = "",
)