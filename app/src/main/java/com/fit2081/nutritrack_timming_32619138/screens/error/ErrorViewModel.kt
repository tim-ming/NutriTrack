package com.fit2081.nutritrack_timming_32619138.screens.error

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ErrorViewModel : ViewModel() {

    private val _navigateToLogin = MutableStateFlow(false)
    val navigateToLogin: StateFlow<Boolean> = _navigateToLogin

    fun onNavigateToLogin() {
        _navigateToLogin.value = true
    }
}