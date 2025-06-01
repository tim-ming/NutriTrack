package com.fit2081.nutritrack_timming_32619138.screens.clinician.dashboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.nutritrack_timming_32619138.BuildConfig
import com.fit2081.nutritrack_timming_32619138.data.patient.Patient
import com.fit2081.nutritrack_timming_32619138.data.patient.PatientRepository
import com.fit2081.nutritrack_timming_32619138.nav.NavigationEvent
import com.fit2081.nutritrack_timming_32619138.screens.Screens
import com.fit2081.nutritrack_timming_32619138.state.LoadingState
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClinicianDashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val patientRepository = PatientRepository(application.applicationContext)

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent.asStateFlow()

    private val _nutritionTableString = MutableStateFlow("")

    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash-001",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    private val _uiState = MutableStateFlow(ClinicianDashboardUiState())
    val uiState: StateFlow<ClinicianDashboardUiState> = _uiState.asStateFlow()

    init {
        _nutritionTableString.value = loadNutritionTable()
        fetch()
    }

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

    private fun fetch() {
        viewModelScope.launch {
            patientRepository.getAllPatients().collect { patients ->
                if (patients.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        avgMaleScore = 0.0f,
                        avgFemaleScore = 0.0f,
                        cardLoadingState = LoadingState.Error("No patient data available"),
                        insightsLoadingState = LoadingState.Error("No patient data available")
                    )
                    return@collect
                }

                val maleScores = patients.filter { it.sex == "Male" }.map { it.heifaTotalScore }
                val femaleScores = patients.filter { it.sex == "Female" }.map { it.heifaTotalScore }

                val avgMale = maleScores.average().toFloat()
                val avgFemale = femaleScores.average().toFloat()

                _uiState.value = _uiState.value.copy(
                    avgMaleScore = avgMale,
                    avgFemaleScore = avgFemale,
                    cardLoadingState = LoadingState.Success
                )

                updateInsights()
            }
        }
    }

    fun updateInsights() {
        _uiState.value = _uiState.value.copy(
            insightsLoadingState = LoadingState.Loading,
            genAiInsights = emptyList()
        )
        viewModelScope.launch {
            patientRepository.getAllPatients().collect { patients ->
                if (patients.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        insightsLoadingState = LoadingState.Error("No patient data available")
                    )
                    return@collect
                } else {
                    _uiState.value = _uiState.value.copy(
                        genAiInsights = getInsights(patients),
                        insightsLoadingState = LoadingState.Success
                    )
                }
            }
        }
    }

    private fun buildPrompt(patients: List<Patient>): String {
        // Calculate basic statistics for context
        val totalPatients = patients.size
        val avgScore = patients.map { it.heifaTotalScore }.average()
        val genderDistribution = patients.groupBy { it.sex }

        val table = _nutritionTableString.value
        val formatted = patients.joinToString("\n") { it.toHumanString() }

        return """
    You are a clinical nutrition analyst. Analyze this patient dataset and the nutrient scoring table, then provide exactly 3 key insights for healthcare professionals.

    ## Dataset Overview:
    - Total Patients: $totalPatients
    - Average HEIFA Score: ${String.format("%.1f", avgScore)}
    - Gender Distribution: ${genderDistribution.mapValues { it.value.size }}
    
    ## Nutrient Scoring Table (CSV format):
    $table

    ## Patient Data:
    $formatted

    ## Analysis Requirements:
    Identify exactly 3 patterns or insights that would be most valuable for clinical decision-making. Focus on:
    **Population Trends**: What demographic patterns emerge from the data?
    
    ## Example format:
    *Females have higher Fruit Intake*,**Findings**:Female patients tend to have higher scores in the Fruit component

    Keep each insight under 30 words and focus on intelligence and insights for healthcare providers. Your response must begin directly with the insights.
    """.trimIndent()
    }

    private suspend fun getInsights(patients: List<Patient>): List<Pair<String, String>> {
        return try {
            val prompt = buildPrompt(patients)
            val response = generativeModel.generateContent(
                content { text(prompt) }
            )

            val responseText =
                response.text ?: return listOf(Pair("Error", "No response generated"))

            // Parse the response into title-content pairs
            parseInsights(responseText)

        } catch (e: Exception) {
            listOf(Pair("Error", "Unable to generate insights: ${e.message}"))
        }
    }

    private fun parseInsights(responseText: String): List<Pair<String, String>> {
        Log.d("GEMINI", responseText)
        val insights = mutableListOf<Pair<String, String>>()

        // Split by lines and process
        val lines = responseText.lines().filter { it.isNotBlank() }

        for (line in lines) {
            when {
                line.contains("*") && line.contains("**Findings**:") -> {
                    val titleMatch = "\\*([^*]+)\\*".toRegex().find(line)
                    val findingsMatch = "\\*\\*Findings\\*\\*:(.+)".toRegex().find(line)

                    if (titleMatch != null && findingsMatch != null) {
                        val title = titleMatch.groupValues[1].trim()
                        val content = findingsMatch.groupValues[1].trim()
                        insights.add(Pair(title, content))
                    }
                }
            }
        }

        // if no structured insights found, try to extract any meaningful content
        if (insights.isEmpty()) {
            _uiState.value = uiState.value.copy(
                insightsNote = "These insights were retrieved in an unexpected format, so presentation may be affected. Please retry the analysis."
            )
            return fallbackParsing(responseText)
        } else {
            _uiState.value = uiState.value.copy(
                insightsNote = ""
            )
        }

        return insights.take(3)
    }

    private fun fallbackParsing(responseText: String): List<Pair<String, String>> {
        val fallbackInsights = mutableListOf<Pair<String, String>>()

        // Try to find sentences that might be insights
        val sentences = responseText.split(".", "!", "?")
            .map { it.trim() }
            .filter { it.length > 20 } // Reasonable insight length
            .take(3)

        sentences.forEachIndexed { index, sentence ->
            val title = "Insight ${index + 1}"
            fallbackInsights.add(Pair(title, sentence))
        }

        // If still nothing, return a generic message
        if (fallbackInsights.isEmpty()) {
            fallbackInsights.add(Pair("Analysis", responseText.take(100) + "..."))
        }


        return fallbackInsights
    }

    fun onBackClick() {
        _navigationEvent.value = NavigationEvent.Navigate(Screens.Clinician.LOGIN)
    }

    fun onNavigationHandled() {
        _navigationEvent.value = null
    }
}