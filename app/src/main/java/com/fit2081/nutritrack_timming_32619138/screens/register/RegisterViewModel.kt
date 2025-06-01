package com.fit2081.nutritrack_timming_32619138.screens.register

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

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent

    private val userRepository = UserRepository(application.applicationContext)
    private val patientRepository = PatientRepository(application.applicationContext)

    init {
        initialiseUidList()
    }

    private fun initialiseUidList() {
        viewModelScope.launch {
            userRepository.getAllUnregisteredUids().collect { uids ->
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

    fun onNameChange(name: TextFieldValue) {
        _uiState.value =
            _uiState.value.copy(name = name, errors = _uiState.value.errors.copy(nameField = ""))
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

    private suspend fun validateRegistrationFieldsVerifyScreen(uiState: RegisterUiState): RegistrationError {
        return uiState.errors.copy(
            uidField = validateUid(uiState.selectedUid),
            phoneNumberField = validatePhoneNumber(uiState.selectedUid, uiState.phoneNumber.text)
        )
    }

    private fun validateRegistrationFieldsPasswordScreen(uiState: RegisterUiState): RegistrationError {
        return uiState.errors.copy(
            passwordField = validatePassword(uiState.password.text),
            confirmPasswordField = validateConfirmPassword(
                uiState.password.text,
                uiState.confirmPassword.text
            )
        )
    }

    private fun validateRegistrationFieldsNameScreen(uiState: RegisterUiState): RegistrationError {
        return uiState.errors.copy(
            nameField = validateName(uiState.name.text)
        )
    }

    fun onRegisterClick() {
        _uiState.value =
            _uiState.value.copy(errors = validateRegistrationFieldsPasswordScreen(_uiState.value))

        if (!_uiState.value.errors.hasErrors()) {
            viewModelScope.launch {
                val userRegistrationResult = userRepository.register(
                    User(
                        userId = _uiState.value.selectedUid,
                        password = _uiState.value.password.text,
                        role = UserRole.PATIENT
                    )
                )

                val patientRegistrationResult = patientRepository.updatePatientName(
                    _uiState.value.selectedUid,
                    _uiState.value.name.text
                )

                if (userRegistrationResult.isSuccess && patientRegistrationResult.isSuccess) {
                    _navigationEvent.value = NavigationEvent.NavigateAndClear(Screens.Login, Screens.Register.PASSWORD)
                    ToastManager.showToast("Account registered!", isSuccess = true)
                } else {
                    val errorMessage = userRegistrationResult.exceptionOrNull()?.message
                        ?: patientRegistrationResult.exceptionOrNull()?.message
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

    private fun validateName(name: String): String {
        return when {
            name.isEmpty() -> ErrorMessages.NAME_EMPTY
            // Using regex for name is controversial, let's just not do it.
            // https://stackoverflow.com/questions/2385701/regular-expression-for-first-and-last-name
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

    fun onPasswordScreenBackClick() {
        _navigationEvent.value = NavigationEvent.Back
    }

    fun onLoginClick() {
        _navigationEvent.value = NavigationEvent.Navigate(Screens.Login)
    }

    fun onVerifyContinueClick() {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(errors = validateRegistrationFieldsVerifyScreen(_uiState.value))
            if (_uiState.value.errors.uidField.isNotEmpty() || _uiState.value.errors.phoneNumberField.isNotEmpty()) {
                return@launch
            }
            _navigationEvent.value = NavigationEvent.Navigate(Screens.Register.NAME)
        }
    }

    fun onNameScreenContinueClick() {
        _uiState.value =
            _uiState.value.copy(errors = validateRegistrationFieldsNameScreen(_uiState.value))
        if (_uiState.value.errors.nameField.isNotEmpty()) {
            return
        }
        _navigationEvent.value = NavigationEvent.Navigate(Screens.Register.PASSWORD)
    }

    fun onNameScreenBackClick() {
        _navigationEvent.value = NavigationEvent.Back
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