package com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.fruit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.nutritrack_timming_32619138.ErrorMessages
import com.fit2081.nutritrack_timming_32619138.data.patient.PatientRepository
import com.fit2081.nutritrack_timming_32619138.state.LoadingState
import com.fit2081.nutritrack_timming_32619138.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val MAX_SUGGESTIONS = 6

class FruitViewModel(application: Application) : AndroidViewModel(application) {
    private val fruitRepository = FruitRepository()
    private val patientRepository = PatientRepository(application.applicationContext)

    private val _uiState = MutableStateFlow(FruitUiState())
    val uiState: StateFlow<FruitUiState> = _uiState.asStateFlow()

    private val sessionManager = SessionManager(application.applicationContext)

    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.Loading)

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Initialize with all fruit names.
    // This originally should be done with the API itself, but FruityVice doesn't have one.
    // So we get manually all fruit names for suggestions, though suggestions can be stale.
    private var allFruitNames: List<String> = emptyList()

    suspend fun loadIsOptimal(): Boolean {
        val uid = sessionManager.getUid()
        if (uid == null) {
            _loadingState.value = LoadingState.RedirectError(ErrorMessages.NOT_LOGGED_IN)
            return false
        }

        val patient = patientRepository.getPatientById(uid)
        if (patient == null) {
            _loadingState.value = LoadingState.RedirectError(ErrorMessages.PATIENT_NOT_FOUND)
            return false
        }

        _loadingState.value = LoadingState.Success
        return patient.fruitHeifaScore >= 10f
    }

    init {
        viewModelScope.launch {
            try {
                val isOptimal = loadIsOptimal()
                _uiState.update { it.copy(isOptimal = isOptimal) }
                if (!isOptimal) {
                    _uiState.update { it.copy(loadingState = LoadingState.Loading) }
                    val fruits = fruitRepository.getAllFruits()
                    allFruitNames = fruits.map { it.name }
                    _uiState.update {
                        it.copy(
                            suggestions = getRandomSuggestions(),
                            chipSuggestions = getRandomSuggestions(),
                            loadingState = LoadingState.Success
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(loadingState = LoadingState.Error("Failed to load fruits")) }
            }
        }
    }

    private fun getRandomSuggestions(): List<String> {
        return allFruitNames.shuffled().take(MAX_SUGGESTIONS)
    }

    fun onActiveChange(isActive: Boolean) {
        _uiState.update { it.copy(active = isActive) }

        if (isActive && _uiState.value.query.isBlank()) {
            val randomSuggestions = getRandomSuggestions()
            _uiState.update { it.copy(suggestions = randomSuggestions) }
        }
    }

    fun onQueryChange(newQuery: String) {
        _uiState.update { it.copy(query = newQuery) }

        val suggestions = if (newQuery.isNotBlank()) {
            allFruitNames.filter {
                it.startsWith(newQuery, ignoreCase = true)
            }.take(MAX_SUGGESTIONS)
        } else if (_uiState.value.active) {
            getRandomSuggestions()
        } else emptyList()

        _uiState.update { it.copy(suggestions = suggestions) }
    }

    fun onClearQuery() {
        if (_uiState.value.query.isNotEmpty()) {
            _uiState.value = _uiState.value.copy(query = "", suggestions = getRandomSuggestions())
        }
    }

    fun onSuggestionSelect(suggestion: String) {
        _uiState.value = _uiState.value.copy(query = suggestion, suggestions = emptyList())
        searchFruit(suggestion)
    }

    fun searchFruit(name: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(loadingState = LoadingState.Loading, searchedBefore = true) }
            try {
                val fruit = fruitRepository.getFruitByName(name)
                _uiState.update {
                    it.copy(
                        fruit = fruit,
                        loadingState = LoadingState.Success
                    )
                }
                _error.value = null
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        fruit = null,
                        loadingState = LoadingState.Error("Fruit not found or network error")
                    )
                }
                _error.value = "Fruit not found or network error"
            }
        }
    }
}