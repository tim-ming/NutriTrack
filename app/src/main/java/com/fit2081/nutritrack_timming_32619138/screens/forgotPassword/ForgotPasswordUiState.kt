package com.fit2081.nutritrack_timming_32619138.screens.forgotPassword

import androidx.compose.ui.text.input.TextFieldValue

data class ForgotPasswordUiState(
    val uids: List<String> = emptyList(),
    val selectedUid: String = "",

    val phoneNumber: TextFieldValue = TextFieldValue(""),
    val password: TextFieldValue = TextFieldValue(""),
    val confirmPassword: TextFieldValue = TextFieldValue(""),
    val passwordVisible: Boolean = false,
    val confirmPasswordVisible: Boolean = false,

    val errors: ForgotPasswordError = ForgotPasswordError(),
)

data class ForgotPasswordError(
    var uidField: String = "",
    var phoneNumberField: String = "",
    var passwordField: String = "",
    var confirmPasswordField: String = "",
    var universal: String = ""
) {
    fun hasErrors(): Boolean {
        return uidField.isNotEmpty() ||
                phoneNumberField.isNotEmpty() ||
                passwordField.isNotEmpty() ||
                confirmPasswordField.isNotEmpty() ||
                universal.isNotEmpty()
    }
}