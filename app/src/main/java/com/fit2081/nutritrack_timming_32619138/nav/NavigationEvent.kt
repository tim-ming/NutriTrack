package com.fit2081.nutritrack_timming_32619138.nav

import com.fit2081.nutritrack_timming_32619138.screens.Screens

/**
 * Abstraction for Navigation Events.
 */
sealed class NavigationEvent {
    data class Navigate(val screen: Screens) : NavigationEvent()
    data class NavigateAndClear(val screen: Screens, val popUpTo: Screens) : NavigationEvent()
    data object Back : NavigationEvent()
}