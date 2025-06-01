package com.fit2081.nutritrack_timming_32619138.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun NavigationEventHandler(
    navigationEvent: NavigationEvent?,
    onNavigationHandled: () -> Unit,
    navigator: Navigator
) {
    LaunchedEffect(navigationEvent){
        when (navigationEvent) {
            is NavigationEvent.Navigate -> {
                navigator.navigateTo(navigationEvent.screen)
                onNavigationHandled()
            }
            is NavigationEvent.NavigateAndClear -> {
                navigator.navigateToAndClear(navigationEvent.screen, navigationEvent.popUpTo)
                onNavigationHandled()
            }
            is NavigationEvent.Back -> {
                navigator.navigateBack()
                onNavigationHandled()
            }
            null -> Unit
        }
    }
}