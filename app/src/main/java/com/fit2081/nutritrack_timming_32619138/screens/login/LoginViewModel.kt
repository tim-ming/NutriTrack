package com.fit2081.nutritrack_timming_32619138.screens.login

import android.app.Application
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.nutritrack_timming_32619138.ErrorMessages
import com.fit2081.nutritrack_timming_32619138.commonUI.toast.ToastManager
import com.fit2081.nutritrack_timming_32619138.data.foodIntake.FoodIntakeRepository
import com.fit2081.nutritrack_timming_32619138.data.user.UserRepository
import com.fit2081.nutritrack_timming_32619138.nav.NavigationEvent
import com.fit2081.nutritrack_timming_32619138.screens.Screens
import com.fit2081.nutritrack_timming_32619138.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent

    private val userRepository = UserRepository(application.applicationContext)
    private val foodIntakeRepository = FoodIntakeRepository(application.applicationContext)
    private val sessionManager = SessionManager(application.applicationContext)

    init {
        getAllUserIds()
    }

    private fun getAllUserIds() {
        viewModelScope.launch {
            userRepository.getAllRegisteredUids().collect { uids ->
                _uiState.value = _uiState.value.copy(
                    uids = uids
                )
            }
        }
    }

    fun onUidSelected(uid: String) {
        _uiState.value = _uiState.value.copy(selectedUid = uid, errorMessage = "")
        updateCanLogin()
    }

    fun onPasswordChanged(password: TextFieldValue) {
        _uiState.value = _uiState.value.copy(password = password, errorMessage = "")
        updateCanLogin()
    }

    private fun updateCanLogin() {
        _uiState.value = _uiState.value.copy(canLogin = canTryLogin())
    }

    private fun canTryLogin(): Boolean {
        val s = _uiState.value
        return s.selectedUid.isNotEmpty() &&
                s.password.text.isNotEmpty()
    }

    fun onLoginClick() {
        if (canTryLogin()) {
            viewModelScope.launch {
                val loginRes =
                    userRepository.login(_uiState.value.selectedUid, _uiState.value.password.text)

                val hasRespondedFoodIntake =
                    foodIntakeRepository.hasResponded(_uiState.value.selectedUid)
                loginRes.fold(
                    onSuccess = {
                        sessionManager.login(_uiState.value.selectedUid)
                        _navigationEvent.value = if (hasRespondedFoodIntake) NavigationEvent.NavigateAndClear(Screens.Home,Screens.Login) else NavigationEvent.NavigateAndClear(Screens.Questionnaire,Screens.Login)
                        ToastManager.showToast(
                            "Logged in",
                            true
                        )
                    },
                    onFailure = { exception ->
                        _uiState.value = _uiState.value.copy(
                            errorMessage = exception.message ?: ErrorMessages.PASSWORD_NOT_CORRECT
                        )
                    }
                )
            }
        } else {
            _uiState.value = _uiState.value.copy(errorMessage = ErrorMessages.MISSING_UID_PASSWORD)
        }
    }


    fun onNavigationHandled() {
        _navigationEvent.value = null
    }

    fun onRegisterClick() {
        _navigationEvent.value = NavigationEvent.Navigate(Screens.Register.BASE_ROUTE)
    }

    fun onForgotPasswordClick() {
        _navigationEvent.value = NavigationEvent.Navigate(Screens.ForgotPassword.BASE_ROUTE)
    }

    fun onTogglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(
            passwordVisible = !_uiState.value.passwordVisible
        )
    }
}