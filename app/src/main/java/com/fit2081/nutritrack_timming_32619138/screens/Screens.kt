package com.fit2081.nutritrack_timming_32619138.screens

sealed class Screens(val route: String) {
    object Login : Screens("login")
    data class Register(val subRoute: String = "") : Screens("register$subRoute") {
        companion object {
            val BASE_ROUTE: Screens = Register()
            val VERIFY: Screens = Register("/verify")
            val NAME: Screens = Register("/name")
            val PASSWORD: Screens = Register("/password")
        }
    }
    data class ForgotPassword(val subRoute: String = "") : Screens("forgotpassword$subRoute") {
        companion object {
            val BASE_ROUTE: Screens = ForgotPassword()
            val VERIFY: Screens = ForgotPassword("/verify")
            val RESET: Screens = ForgotPassword("/reset")
        }
    }
    object Home : Screens("home")
    object Insights : Screens("insights")
    object Questionnaire : Screens("questionnaire")
    object Welcome : Screens("welcome")
    object NutriCoach : Screens("nutricoach")
    object Settings : Screens("settings")
    data class Clinician(val subRoute: String = "") : Screens("clinician$subRoute"){
        companion object {
            val LOGIN: Screens = Clinician("/login")
            val DASHBOARD: Screens = Clinician("/dashboard")
        }
    }
    data class Error(val errorMessage: String) : Screens("error/$errorMessage") {
        companion object {
            const val PARAMETER = "error"
            const val BASE_ROUTE = "error/{$PARAMETER}"
        }
    }
}