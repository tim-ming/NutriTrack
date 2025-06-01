package com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.tip

import android.app.Application
import androidx.compose.ui.semantics.text
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.nutritrack_timming_32619138.BuildConfig
import com.fit2081.nutritrack_timming_32619138.ErrorMessages
import com.fit2081.nutritrack_timming_32619138.data.nutriCoachTips.NutriCoachTip
import com.fit2081.nutritrack_timming_32619138.data.nutriCoachTips.NutriCoachTipRepository
import com.fit2081.nutritrack_timming_32619138.data.patient.Patient
import com.fit2081.nutritrack_timming_32619138.data.patient.PatientRepository
import com.fit2081.nutritrack_timming_32619138.state.LoadingState
import com.fit2081.nutritrack_timming_32619138.util.SessionManager
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TipViewModel(application: Application) : AndroidViewModel(application) {
    private val tipRepository = NutriCoachTipRepository(application.applicationContext)
    private val sessionManager = SessionManager(application.applicationContext)

    private val patientRepository = PatientRepository(application.applicationContext)

    private val _patient = MutableStateFlow<Patient?>(null)
    private val _nutritionTableString = MutableStateFlow("")

    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.Loading)
    val loadingState: StateFlow<LoadingState> = _loadingState.asStateFlow()

    private val _uiState: MutableStateFlow<TipUiState> = MutableStateFlow(TipUiState())
    val uiState: StateFlow<TipUiState> = _uiState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash-001",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    private fun loadNutritionTable(): String {
        return try {
            getApplication<Application>().assets
                .open("nutrient_scoring_table.csv")
                .bufferedReader()
                .use { it.readText() }
        } catch (e: Exception) {
            ""
        }
    }

    init {
        _nutritionTableString.value = loadNutritionTable()
        loadPatient()
        loadAllTips()
    }


    fun fetchAndProcessTip() {
        _uiState.value = _uiState.value.copy(
            promptedBefore = true
        )
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(messageLoadingState = LoadingState.Loading)
            val patient = _patient.value
            if (patient == null) {
                _uiState.value = _uiState.value.copy(
                    messageLoadingState = LoadingState.Error("No patient data available.")
                )
                return@launch
            }
            val table = _nutritionTableString.value
            val patientScores = patient.toHumanString()

            val prompt = """
            Let's role play. Imagine you are a nutrition coach assistant talking to me, your patient. Based on the nutrient scoring table below and the patient's scores, write a short, encouraging recommendation (2–3 sentences) to help the person improve their diet. Be supportive and friendly. **The recommendation should begin directly with the advice, let the user know what they are lacking in. Advice should not related to the scores, but rather real-life advice.**

            Nutrient Scoring Table (CSV format):
            $table

            $patientScores
        """.trimIndent()

            val result: Result<String> = getGeminiMessage(prompt)

            result.fold(
                onSuccess = { messageText ->
                    _uiState.value = _uiState.value.copy(
                        messageLoadingState = LoadingState.Success,
                        message = messageText
                    )
                    tipRepository.insert(
                        NutriCoachTip(
                            userId = patient.userId,
                            message = messageText,
                            time = LocalDateTime.now(),
                            prompt = prompt
                        )
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        messageLoadingState = LoadingState.Error(error.message ?: ErrorMessages.UNKNOWN_ERROR),
                        message = ""
                    )
                }
            )
        }
    }

    fun onDialogVisibleChange(visible: Boolean){
        if (visible){
            _uiState.value = _uiState.value.copy(
                tipsLoadingState = LoadingState.Loading,
            )
            loadAllTips()
            _uiState.value = _uiState.value.copy(
                tipsLoadingState = LoadingState.Success,
            )
        }
        _uiState.value = _uiState.value.copy(
            dialogOpen = visible
        )
    }

    private suspend fun getGeminiMessage(prompt: String): Result<String> {
        _uiState.value = _uiState.value.copy(
            messageLoadingState = LoadingState.Loading,
            message = ""
        )

        return try {
            val response = generativeModel.generateContent(
                content {
                    text(prompt)
                }
            )

            response.text?.let { outputContent ->
                Result.success(outputContent.trimIndent())
            } ?: run {
                Result.failure(Exception("No content received from Gemini model."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
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
            _patient.value = patient
        }
    }

    private fun loadAllTips() {
        viewModelScope.launch {
            val uid = sessionManager.getUid()
            if (uid == null) {
                _loadingState.value = LoadingState.RedirectError(ErrorMessages.NOT_LOGGED_IN)
                return@launch
            }
            try {
                val tips = tipRepository.getAllTipsByUserId(uid)
                _uiState.value = _uiState.value.copy(
                    tipHistory = tips,
                    messageLoadingState = LoadingState.Success
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    messageLoadingState = LoadingState.Error("Failed to load tips.")
                )
            }
        }
    }
}