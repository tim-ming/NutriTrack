package com.fit2081.nutritrack_timming_32619138.screens.clinician.login

import android.app.Application
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import com.fit2081.nutritrack_timming_32619138.CLINICIAN_PASSPHRASE
import com.fit2081.nutritrack_timming_32619138.ErrorMessages
import com.fit2081.nutritrack_timming_32619138.nav.NavigationEvent
import com.fit2081.nutritrack_timming_32619138.screens.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ClinicianLoginViewModel (application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(ClinicianLoginUiState())
    val uiState: StateFlow<ClinicianLoginUiState> = _uiState.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent

    fun onPassphraseChange(passphraseField: TextFieldValue){
        _uiState.value =
            _uiState.value.copy(
                passphraseField = passphraseField,
                errorMessage = ""
            )
    }

    fun onTogglePassphraseVisibility(){
        _uiState.value = _uiState.value.copy(passphraseVisible = !_uiState.value.passphraseVisible)
    }

    private fun validatePassphraseField(){
        if (_uiState.value.passphraseField.text != CLINICIAN_PASSPHRASE){
            _uiState.value =
                _uiState.value.copy(
                    errorMessage = ErrorMessages.PASSPHRASE_NOT_CORRECT
                )
        }
    }

    fun onBackClick(){
        _navigationEvent.value = NavigationEvent.Navigate(Screens.Settings)
    }

    fun onLoginClick() {
        validatePassphraseField()
        if (_uiState.value.errorMessage.isEmpty()) {
            _navigationEvent.value = NavigationEvent.Navigate(Screens.Clinician.DASHBOARD)
        }
    }

    fun onNavigationHandled() {
        _navigationEvent.value = null
    }
}