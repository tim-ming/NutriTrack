package com.fit2081.nutritrack_timming_32619138.state

sealed class LoadingState {
    data object Loading : LoadingState()
    data object Success: LoadingState()
    data class Error(val message: String) : LoadingState()
    data class RedirectError(val message: String) : LoadingState()
}

