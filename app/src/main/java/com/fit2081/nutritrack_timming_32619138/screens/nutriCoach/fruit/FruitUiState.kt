package com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.fruit

import com.fit2081.nutritrack_timming_32619138.state.LoadingState

data class FruitUiState(
    val isOptimal: Boolean = true,
    val query: String = "",
    val suggestions: List<String> = emptyList(),
    val chipSuggestions: List<String> = emptyList(),
    val fruit: Fruit? = null,
    val active: Boolean = false,
    val loadingState: LoadingState = LoadingState.Loading,
    val searchedBefore : Boolean = false
)
