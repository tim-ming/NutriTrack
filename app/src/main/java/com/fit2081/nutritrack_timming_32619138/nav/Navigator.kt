package com.fit2081.nutritrack_timming_32619138.nav

import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fit2081.nutritrack_timming_32619138.screens.Screens

class Navigator(private val navController: NavHostController) {
    fun navigateTo(screen: Screens) {
        val currentRoute = navController.currentBackStackEntry?.destination?.route

        if (currentRoute != screen.route) {
            navController.navigate(screen.route)
        }
    }

    fun navigateToAndClear(screen: Screens, clearUntil: Screens) {
        navController.navigate(screen.route) {
            popUpTo(0) { inclusive = true }
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}