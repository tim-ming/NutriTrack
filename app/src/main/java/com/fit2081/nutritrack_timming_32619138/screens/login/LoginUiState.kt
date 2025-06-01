package com.fit2081.nutritrack_timming_32619138.screens.login

import androidx.compose.ui.text.input.TextFieldValue

data class LoginUiState(
    val uids: List<String> = emptyList(),
    val selectedUid: String = "",
    val password: TextFieldValue = TextFieldValue(""),
    val passwordVisible: Boolean = false,
    val canLogin: Boolean = false,
    val errorMessage: String = "",
)