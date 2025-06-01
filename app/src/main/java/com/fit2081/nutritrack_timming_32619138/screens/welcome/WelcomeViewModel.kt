package com.fit2081.nutritrack_timming_32619138.screens.welcome

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fit2081.nutritrack_timming_32619138.nav.NavigationEvent
import com.fit2081.nutritrack_timming_32619138.screens.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WelcomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent

    fun onNavigateToLogin() {
        _navigationEvent.value = NavigationEvent.Navigate(Screens.Login)
    }

    fun onNavigationHandled() {
        _navigationEvent.value = null
    }

}