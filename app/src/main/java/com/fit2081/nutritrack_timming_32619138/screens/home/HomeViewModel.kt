package com.fit2081.nutritrack_timming_32619138.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.nutritrack_timming_32619138.ErrorMessages
import com.fit2081.nutritrack_timming_32619138.data.foodIntake.FoodIntakeRepository
import com.fit2081.nutritrack_timming_32619138.state.LoadingState
import com.fit2081.nutritrack_timming_32619138.data.patient.PatientRepository
import com.fit2081.nutritrack_timming_32619138.nav.NavigationEvent
import com.fit2081.nutritrack_timming_32619138.screens.Screens
import com.fit2081.nutritrack_timming_32619138.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val sessionManager = SessionManager(application.applicationContext)
    private val patientRepository = PatientRepository(application.applicationContext)
    private val foodIntakeRepository = FoodIntakeRepository(application.applicationContext)
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.Loading)
    val loadingState: StateFlow<LoadingState> = _loadingState

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent

    init {
        fetch()
    }

    private fun fetch() {
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

            val hasResponded = foodIntakeRepository.hasResponded(uid)
            _loadingState.value = LoadingState.Success
            _uiState.value =
                HomeUiState(
                    patient,
                    hasRespondedSurvey = hasResponded
                )
        }
    }

    fun onQuestionnaireClicked() {
        _navigationEvent.value = NavigationEvent.Navigate(Screens.Questionnaire)
    }

    fun onSeeAllScoresClicked() {
        _navigationEvent.value = NavigationEvent.Navigate(Screens.Insights)
    }

    fun onEnhanceDietClicked(){
        _navigationEvent.value = NavigationEvent.Navigate(Screens.NutriCoach)
    }

    fun onNavigationHandled() {
        _navigationEvent.value = null
    }
}