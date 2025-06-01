package com.fit2081.nutritrack_timming_32619138.commonUI.toast

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object ToastManager {
    private val _toastMessage = MutableStateFlow("")
    val toastMessage: StateFlow<String> = _toastMessage

    private val _showMessage = MutableStateFlow(false)
    val showMessage: StateFlow<Boolean> = _showMessage

    private val _isSuccess = MutableStateFlow(true)
    val isSuccess: StateFlow<Boolean> = _isSuccess

    fun showToast(message: String, isSuccess: Boolean) {
        _toastMessage.value = message
        _isSuccess.value = isSuccess
        _showMessage.value = true
    }

    fun hideToast() {
        _showMessage.value = false
    }
}