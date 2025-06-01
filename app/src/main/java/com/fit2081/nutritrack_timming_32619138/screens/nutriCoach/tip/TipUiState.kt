package com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.tip

import com.fit2081.nutritrack_timming_32619138.data.nutriCoachTips.NutriCoachTip
import com.fit2081.nutritrack_timming_32619138.state.LoadingState
import java.time.LocalDateTime

data class TipUiState(
    val message: String = "",
    val prompt: String = "",
    val time: LocalDateTime = LocalDateTime.now(),
    val tipHistory : List<NutriCoachTip> = emptyList(),
    val messageLoadingState: LoadingState = LoadingState.Loading,
    val tipsLoadingState: LoadingState = LoadingState.Loading,
    val dialogOpen: Boolean = false,
    val promptedBefore: Boolean = false
)