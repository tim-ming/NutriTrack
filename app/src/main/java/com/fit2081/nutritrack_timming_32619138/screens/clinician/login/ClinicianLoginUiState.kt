package com.fit2081.nutritrack_timming_32619138.screens.clinician.login

import androidx.compose.ui.text.input.TextFieldValue

data class ClinicianLoginUiState(
    val passphraseField: TextFieldValue = TextFieldValue(""),
    val passphraseVisible: Boolean = false,
    val errorMessage: String = "",
)