package com.fit2081.nutritrack_timming_32619138.screens.questionnaire

import PersonaType
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.nutritrack_timming_32619138.commonUI.toast.ToastManager
import com.fit2081.nutritrack_timming_32619138.data.foodIntake.FoodIntakeRepository
import com.fit2081.nutritrack_timming_32619138.nav.NavigationEvent
import com.fit2081.nutritrack_timming_32619138.screens.Screens
import com.fit2081.nutritrack_timming_32619138.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class QuestionnaireViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(QuestionnaireUiState())
    val uiState: StateFlow<QuestionnaireUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent

    private val foodIntakeRepository = FoodIntakeRepository(application.applicationContext)

    private val sessionManager = SessionManager(application.applicationContext)

    init {
        val uid = sessionManager.getUid()
        if (uid != null) {
            viewModelScope.launch {
                val data = foodIntakeRepository.getResponseForPatient(uid)
                _uiState.value = _uiState.value.copyFromEntity(data)
            }
        }
    }

    fun toggleFoodCategory(category: String) {
        val current = _uiState.value.foodCategories.toMutableSet()
        if (current.contains(category)) current.remove(category) else current.add(category)
        _uiState.value = _uiState.value.copy(foodCategories = current)
    }

    fun selectPersona(persona: PersonaType) {
        _uiState.value = _uiState.value.copy(persona = persona)
    }

    fun updateTiming(index: Int, time: LocalDateTime) {
        val updated = _uiState.value.timings.toMutableList()
        updated[index] = updated[index].copy(time = time)
        _uiState.value = _uiState.value.copy(timings = updated)
    }

    fun onSubmit() {
        val uid = sessionManager.getUid() ?: return
        viewModelScope.launch {
            val current = _uiState.value
            if (current.persona != null) {
                foodIntakeRepository.updateResponse(current.toEntity(uid))
                _navigationEvent.value = NavigationEvent.Navigate(Screens.Home)
                ToastManager.showToast("Responses updated!",true)
            }
        }
    }

    fun onBack(){
        _navigationEvent.value = NavigationEvent.Navigate(Screens.Home)
    }

    fun onNavigationHandled(){
        _navigationEvent.value = null
    }
}