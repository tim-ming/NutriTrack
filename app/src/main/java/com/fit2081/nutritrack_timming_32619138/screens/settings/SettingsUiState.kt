package com.fit2081.nutritrack_timming_32619138.screens.settings

import androidx.compose.ui.text.input.TextFieldValue
import com.fit2081.nutritrack_timming_32619138.data.patient.Patient

data class SettingsUiState(
    val patient: Patient = Patient.UNKNOWN,
    val isNameDropdownVisible: Boolean = false,
    val nameField: TextFieldValue = TextFieldValue(patient.name),
    val isClinicianDropdownVisible: Boolean = false,
    val clinicianField: TextFieldValue = TextFieldValue(patient.name),
    val errorMessage: String = ""
)