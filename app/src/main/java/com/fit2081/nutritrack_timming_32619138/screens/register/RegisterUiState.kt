package com.fit2081.nutritrack_timming_32619138.screens.register

import androidx.compose.ui.text.input.TextFieldValue

data class RegisterUiState(
    val uids: List<String> = emptyList(),
    val selectedUid: String = "",

    val name: TextFieldValue = TextFieldValue(""),
    val phoneNumber: TextFieldValue = TextFieldValue(""),
    val password: TextFieldValue = TextFieldValue(""),
    val confirmPassword: TextFieldValue = TextFieldValue(""),
    val passwordVisible: Boolean = false,
    val confirmPasswordVisible: Boolean = false,

    val errors: RegistrationError = RegistrationError(),
)

data class RegistrationError(
    var nameField: String = "",
    var uidField: String = "",
    var phoneNumberField: String = "",
    var passwordField: String = "",
    var confirmPasswordField: String = "",
    var universal: String = ""
) {
    fun hasErrors(): Boolean {
        return nameField.isNotEmpty() ||
                uidField.isNotEmpty() ||
                phoneNumberField.isNotEmpty() ||
                passwordField.isNotEmpty() ||
                confirmPasswordField.isNotEmpty() ||
                universal.isNotEmpty()
    }
}