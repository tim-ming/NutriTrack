package com.fit2081.nutritrack_timming_32619138.screens.forgotPassword

import android.app.Application
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.nutritrack_timming_32619138.ErrorMessages
import com.fit2081.nutritrack_timming_32619138.commonUI.toast.ToastManager
import com.fit2081.nutritrack_timming_32619138.data.patient.PatientRepository
import com.fit2081.nutritrack_timming_32619138.data.user.User
import com.fit2081.nutritrack_timming_32619138.data.user.UserRepository
import com.fit2081.nutritrack_timming_32619138.data.user.UserRole
import com.fit2081.nutritrack_timming_32619138.nav.NavigationEvent
import com.fit2081.nutritrack_timming_32619138.screens.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent

    private val userRepository = UserRepository(application.applicationContext)
    private val patientRepository = PatientRepository(application.applicationContext)

    init {
        initialiseUidList()
    }

    private fun initialiseUidList() {
        viewModelScope.launch {
            userRepository.getAllRegisteredUids().collect { uids ->
                _uiState.value = _uiState.value.copy(
                    uids = uids
                )
            }
        }
    }

    fun onUidSelected(uid: String) {
        _uiState.value = _uiState.value.copy(
            selectedUid = uid,
            errors = _uiState.value.errors.copy(uidField = "")
        )
    }

    fun onPhoneNumberChange(phoneNumber: TextFieldValue) {
        _uiState.value = _uiState.value.copy(
            phoneNumber = phoneNumber,
            errors = _uiState.value.errors.copy(phoneNumberField = "")
        )
    }

    fun onPasswordChange(password: TextFieldValue) {
        _uiState.value = _uiState.value.copy(
            password = password,
            errors = _uiState.value.errors.copy(passwordField = "")
        )
    }

    fun onConfirmPasswordChange(confirmPassword: TextFieldValue) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = confirmPassword,
            errors = _uiState.value.errors.copy(confirmPasswordField = "")
        )
    }

    private suspend fun validateRegistrationFieldsVerifyScreen(uiState: ForgotPasswordUiState): ForgotPasswordError {
        return uiState.errors.copy(
            uidField = validateUid(uiState.selectedUid),
            phoneNumberField = validatePhoneNumber(uiState.selectedUid, uiState.phoneNumber.text)
        )
    }

    private fun validateRegistrationFieldsPasswordScreen(uiState: ForgotPasswordUiState): ForgotPasswordError {
        return uiState.errors.copy(
            passwordField = validatePassword(uiState.password.text),
            confirmPasswordField = validateConfirmPassword(
                uiState.password.text,
                uiState.confirmPassword.text
            )
        )
    }

    fun onConfirmClick() {
        _uiState.value =
            _uiState.value.copy(errors = validateRegistrationFieldsPasswordScreen(_uiState.value))

        if (!_uiState.value.errors.hasErrors()) {
            viewModelScope.launch {
                val updatePasswordResult = userRepository.updatePassword(_uiState.value.selectedUid, _uiState.value.password.text)
                if (updatePasswordResult.isSuccess) {
                    _navigationEvent.value = NavigationEvent.NavigateAndClear(Screens.Login,Screens.ForgotPassword.RESET)
                    ToastManager.showToast("Password changed!", isSuccess = true)
                } else {
                    val errorMessage = updatePasswordResult.exceptionOrNull()?.message
                        ?: ErrorMessages.REGISTRATION_FAILED

                    _uiState.value = _uiState.value.copy(
                        errors = _uiState.value.errors.copy(universal = errorMessage)
                    )
                }
            }
        }
    }

    private fun validateUid(uid: String): String {
        return when {
            uid.isEmpty() -> ErrorMessages.MISSING_UID_PASSWORD
            else -> ""
        }
    }

    private suspend fun validatePhoneNumber(id: String, phoneNumber: String): String {
        val patient = patientRepository.getPatientById(id)

        return when {
            patient == null -> ErrorMessages.PATIENT_NOT_FOUND
            phoneNumber.isEmpty() -> ErrorMessages.PHONE_NUMBER_EMPTY
            !phoneNumber.all { it.isDigit() } -> ErrorMessages.PHONE_NUMBER_NOT_DIGITS
            phoneNumber != patient.phoneNumber -> ErrorMessages.PHONE_NUMBER_NOT_MATCH
            else -> ""
        }
    }

    private fun validatePassword(password: String): String {
        return when {
            password.length < 8 -> ErrorMessages.PASSWORD_TOO_SHORT
            else -> ""
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): String {
        return when {
            confirmPassword.isEmpty() -> ErrorMessages.PASSWORD_NOT_CONFIRM
            password != confirmPassword -> ErrorMessages.PASSWORD_NOT_MATCH
            else -> ""
        }
    }

    fun onVerifyScreenBackClick() {
        _navigationEvent.value = NavigationEvent.Back
    }

    fun onPasswordScreenBackClick() {
        _navigationEvent.value = NavigationEvent.Back
    }

    fun onVerifyContinueClick() {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(errors = validateRegistrationFieldsVerifyScreen(_uiState.value))
            if (_uiState.value.errors.uidField.isNotEmpty() || _uiState.value.errors.phoneNumberField.isNotEmpty()) {
                return@launch
            }
            _navigationEvent.value = NavigationEvent.Navigate(Screens.ForgotPassword.RESET)
        }
    }

    fun onNavigationHandled() {
        _navigationEvent.value = null
    }

    fun onTogglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(
            passwordVisible = !_uiState.value.passwordVisible
        )
    }

    fun onToggleConfirmPasswordVisibility() {
        _uiState.value = _uiState.value.copy(
            confirmPasswordVisible = !_uiState.value.confirmPasswordVisible
        )
    }
}