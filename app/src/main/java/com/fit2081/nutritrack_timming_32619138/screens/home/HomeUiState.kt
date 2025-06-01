package com.fit2081.nutritrack_timming_32619138.screens.home

import com.fit2081.nutritrack_timming_32619138.data.patient.Patient

data class HomeUiState(
    val patient: Patient = Patient.UNKNOWN,
    val hasRespondedSurvey: Boolean = false
)