package com.fit2081.nutritrack_timming_32619138.state

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fit2081.nutritrack_timming_32619138.nav.Navigator
import com.fit2081.nutritrack_timming_32619138.screens.Screens

/**
 * A composable that handles the state of a screen.
 * Can be used to implement skeleton UI while loading, error handling, and redirection.
 */
@Composable
fun StateHandler(
    state: LoadingState,
    navigator: Navigator,
    onLoading: @Composable () -> Unit = {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    },
    onSuccess: @Composable () -> Unit,
    onError: @Composable (String) -> Unit = { message ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Error: $message", color = MaterialTheme.colorScheme.error)
        }
    }
) {
    when (state) {
        is LoadingState.Loading -> onLoading()

        is LoadingState.Success -> onSuccess()

        is LoadingState.Error -> onError(state.message)

        is LoadingState.RedirectError -> {
            LaunchedEffect(state.message) {
                navigator.navigateToAndClear(
                    Screens.Error(state.message),
                    Screens.Error(state.message)
                )
            }

            // Optional fallback UI during navigation
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}