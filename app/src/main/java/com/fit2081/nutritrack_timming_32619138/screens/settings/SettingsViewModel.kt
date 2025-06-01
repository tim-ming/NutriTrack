package com.fit2081.nutritrack_timming_32619138.screens.settings

import android.app.Application
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.nutritrack_timming_32619138.ErrorMessages
import com.fit2081.nutritrack_timming_32619138.commonUI.toast.ToastManager
import com.fit2081.nutritrack_timming_32619138.data.patient.PatientRepository
import com.fit2081.nutritrack_timming_32619138.nav.NavigationEvent
import com.fit2081.nutritrack_timming_32619138.screens.Screens
import com.fit2081.nutritrack_timming_32619138.state.LoadingState
import com.fit2081.nutritrack_timming_32619138.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val sessionManager = SessionManager(application.applicationContext)
    private val patientRepository = PatientRepository(application.applicationContext)

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.Loading)
    val loadingState: StateFlow<LoadingState> = _loadingState.asStateFlow()

    init {
        loadPatient()
    }

    private fun loadPatient() {
        viewModelScope.launch {
            val uid = sessionManager.getUid()
            if (uid == null) {
                _loadingState.value = LoadingState.RedirectError(ErrorMessages.NOT_LOGGED_IN)
                return@launch
            }

            val patient = patientRepository.getPatientById(uid)
            if (patient == null) {
                _loadingState.value = LoadingState.RedirectError(ErrorMessages.PATIENT_NOT_FOUND)
                return@launch
            }

            _loadingState.value = LoadingState.Success
            _uiState.value = SettingsUiState(patient)
        }
    }

    fun onToggleEditName() {
        val editing = _uiState.value.isNameDropdownVisible
        _uiState.value =
            _uiState.value.copy(isNameDropdownVisible = !editing)
    }

    fun onNameChange(newName: TextFieldValue) {
        updateNameField(tempName = newName)
    }

    private fun updateNameField(tempName: TextFieldValue) {
        _uiState.value =
            _uiState.value.copy(
                nameField = tempName
            )
    }

    private fun validateName(name: String): String {
        return when {
            name.isEmpty() -> ErrorMessages.NAME_EMPTY
            // Using regex for name is controversial, let's just not do it.
            // https://stackoverflow.com/questions/2385701/regular-expression-for-first-and-last-name
            else -> ""
        }
    }

    fun onSaveNameChange() {
        viewModelScope.launch {
            val errorMessage = validateName(_uiState.value.nameField.text)
            if (errorMessage.isNotEmpty()) {
                _uiState.value = _uiState.value.copy(errorMessage = errorMessage)
                return@launch
            }
            _uiState.value = _uiState.value.copy(errorMessage = "")

            val res = patientRepository.updatePatientName(
                _uiState.value.patient.userId,
                _uiState.value.nameField.text
            )

            res.fold(
                onSuccess = {
                    val updatedPatient = _uiState.value.patient.copy(
                        name = _uiState.value.nameField.text
                    )
                    _uiState.value =
                        _uiState.value.copy(
                            patient = updatedPatient,
                            isNameDropdownVisible = false
                        )
                    ToastManager.showToast("Name updated", isSuccess = true)
                },
                onFailure = {
                    ToastManager.showToast("Failed to update name", isSuccess = false)
                }
            )
        }
    }

    fun onLogoutClick() {
        viewModelScope.launch {
            sessionManager.logout()
            _navigationEvent.value =
                NavigationEvent.NavigateAndClear(Screens.Welcome, Screens.Settings)
        }
    }

    fun onNavigationHandled() {
        _navigationEvent.value = null
    }

    fun onClinicianLoginRowClick() {
        _navigationEvent.value = NavigationEvent.Navigate(Screens.Clinician.LOGIN)
    }

}

