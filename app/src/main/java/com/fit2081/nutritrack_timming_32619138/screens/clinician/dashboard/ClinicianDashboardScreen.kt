package com.fit2081.nutritrack_timming_32619138.screens.clinician.dashboard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fit2081.nutritrack_timming_32619138.R
import com.fit2081.nutritrack_timming_32619138.commonUI.TopBackButton
import com.fit2081.nutritrack_timming_32619138.commonUI.icons.Stars
import com.fit2081.nutritrack_timming_32619138.commonUI.modifier.shimmerLoading
import com.fit2081.nutritrack_timming_32619138.nav.NavigationEventHandler
import com.fit2081.nutritrack_timming_32619138.nav.Navigator
import com.fit2081.nutritrack_timming_32619138.state.LoadingState
import com.fit2081.nutritrack_timming_32619138.ui.theme.AccentDark
import com.fit2081.nutritrack_timming_32619138.ui.theme.AccentLight
import com.fit2081.nutritrack_timming_32619138.ui.theme.NutriTrackTheme
import com.fit2081.nutritrack_timming_32619138.ui.theme.Orange
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary100
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary200
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary400
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary500
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary600
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary900
import com.fit2081.nutritrack_timming_32619138.ui.theme.Red

@Composable
fun ClinicianDashboardScreen(
    innerPadding: PaddingValues,
    navigator: Navigator,
    viewModel: ClinicianDashboardViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()

    NavigationEventHandler(
        navigationEvent = navigationEvent,
        navigator = navigator,
        onNavigationHandled = viewModel::onNavigationHandled
    )

    ClinicianDashboardContent(
        innerPadding = innerPadding,
        onReanalyse = viewModel::updateInsights,
        uiState = uiState,
        onBackClick = viewModel::onBackClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicianDashboardContent(
    innerPadding: PaddingValues,
    uiState: ClinicianDashboardUiState,
    onReanalyse: () -> Unit,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Scaffold(
        floatingActionButton = {
            AnimatedFAB(
                onClick = onReanalyse,
                uiState = uiState,
            )
        }
    ) {
        it
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF9F9F9))
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
                .padding(top = 8.dp)
                .verticalScroll(
                    scrollState,
                    enabled = true
                )
        ) {
            TopBackButton(onBackClick, "Clinician Login")
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Clinician Insights",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(24.dp))

            AnimatedContent(
                targetState = uiState.cardLoadingState,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(300)) + slideInVertically(
                        initialOffsetY = { 40 } // slide up from below
                    )).togetherWith(fadeOut(animationSpec = tween(200)) + slideOutVertically(
                        targetOffsetY = { -40 } // slide out upwards
                    ))
                },
                label = "Fruit Initial State AnimatedContent"
            ) { state ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    when (state) {
                        is LoadingState.Success -> {
                            AnimatedStatsCard(
                                "Avg. HEIFA (Male)", uiState.avgMaleScore,
                                {
                                    Box(modifier = Modifier.size(72.dp)) {
                                        Image(
                                            painter = painterResource(id = R.drawable.boy),
                                            contentDescription = "Profile image",
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }
                            )
                            AnimatedStatsCard(
                                "Avg. HEIFA (Female)", uiState.avgFemaleScore,
                                {
                                    Image(
                                        painter = painterResource(id = R.drawable.girl),
                                        contentDescription = "Profile image",
                                        modifier = Modifier.size(72.dp)
                                    )
                                }
                            )
                        }

                        is LoadingState.Loading -> {
                            repeat(2) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(140.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .shimmerLoading()
                                )
                            }
                        }

                        is LoadingState.Error -> {
                            Text(text = state.message)
                        }

                        else -> {}
                    }
                }
            }
            Spacer(Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "AI analysis",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Icon(
                        imageVector = Stars,
                        contentDescription = "AI Stars",
                        tint = AccentDark,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }

            }
            Spacer(Modifier.height(16.dp))

            AnimatedContent(
                targetState = uiState.insightsLoadingState,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(300)) + slideInVertically(
                        initialOffsetY = { 40 } // slide up from below
                    )).togetherWith(fadeOut(animationSpec = tween(200)) + slideOutVertically(
                        targetOffsetY = { -40 } // slide out upwards
                    ))
                },
                label = "Fruit Initial State AnimatedContent"
            ) { state ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    when (state) {
                        is LoadingState.Success -> {
                            uiState.genAiInsights.forEachIndexed { index, (title, content) ->
                                Column(
                                    modifier = Modifier
                                        .shadow(
                                            8.dp,
                                            RoundedCornerShape(12.dp),
                                            ambientColor = Primary100,
                                            spotColor = Primary500
                                        )
                                        .background(
                                            Color.White,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        "$title",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        "$content",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Primary600
                                    )
                                }
                            }
                        }

                        is LoadingState.Loading -> {
                            repeat(3) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .shimmerLoading()
                                )
                            }
                        }

                        is LoadingState.Error -> {
                            Text(text = state.message)
                        }

                        else -> {}
                    }
                }
            }
            Spacer(Modifier.height(80.dp))
        }
    }
}


@Composable
fun RowScope.AnimatedStatsCard(title: String, score: Float, image: @Composable () -> Unit) {
    var animatedScore by rememberSaveable { mutableFloatStateOf(0.0f) }
    val color = when {
        score > 80f -> {
            AccentDark
        }

        score in 51f..80f -> {
            Orange
        }

        else -> {
            Red
        }
    }
    LaunchedEffect(score) {
        animate(
            initialValue = 0.0f,
            targetValue = score,
            animationSpec = tween(durationMillis = 1200, easing = LinearOutSlowInEasing)
        ) { value, _ -> animatedScore = value }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                8.dp,
                RoundedCornerShape(12.dp),
                ambientColor = Primary100,
                spotColor = Primary500
            )
            .border(
                width = 1.dp,
                color = Primary200,
                shape = RoundedCornerShape(12.dp)
            )
            .background(Color.White)
            .weight(1f)
            .padding(vertical = 16.dp)
    ) {
        image()
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, style = MaterialTheme.typography.bodyMedium, color = Primary600)
            Text(
                text = String.format("%.2f", animatedScore),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.SemiBold,
                color = color
            )
        }
    }
}

@Composable
fun AnimatedFAB(
    onClick: () -> Unit,
    uiState: ClinicianDashboardUiState,
    modifier: Modifier = Modifier
) {
    val isLoading = LoadingState.Loading == uiState.insightsLoadingState

    val containerColor by animateColorAsState(
        targetValue = if (isLoading) Primary400 else AccentDark,
        animationSpec = tween(300),
        label = "container color"
    )

    val fabWidth by animateDpAsState(
        targetValue = if (isLoading) 120.dp else 180.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "fab width"
    )

    FloatingActionButton(
        containerColor = containerColor,
        contentColor = AccentLight,
        modifier = modifier
            .padding(16.dp)
            .width(fabWidth)
            .height(56.dp),
        onClick = {
            if (!isLoading) {
                onClick()
            }
        },
    ) {
        AnimatedContent(
            targetState = isLoading,
            transitionSpec = {
                if (targetState) {
                    // Loading state entering
                    slideInHorizontally { width -> width } + fadeIn() togetherWith
                            slideOutHorizontally { width -> -width } + fadeOut()
                } else {
                    // Normal state entering
                    slideInHorizontally { width -> -width } + fadeIn() togetherWith
                            slideOutHorizontally { width -> width } + fadeOut()
                }.using(SizeTransform(clip = false))
            },
            label = "fab content"
        ) { loading ->
            if (loading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }
            } else {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Stars,
                        contentDescription = "Reanalyze",
                        modifier = Modifier
                            .size(24.dp)
                    )

                    AnimatedVisibility(
                        visible = !isLoading,
                        enter = expandHorizontally() + fadeIn(),
                        exit = shrinkHorizontally() + fadeOut()
                    ) {
                        Spacer(modifier = Modifier.size(12.dp))
                    }

                    AnimatedVisibility(
                        visible = !isLoading,
                        enter = expandHorizontally() + fadeIn(
                            animationSpec = tween(300, delayMillis = 300)
                        ),
                        exit = shrinkHorizontally() + fadeOut()
                    ) {
                        Text(
                            "New analysis",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun Preview() {
    NutriTrackTheme {
        ClinicianDashboardContent(
            innerPadding = PaddingValues(0.dp),
            onReanalyse = {},
            onBackClick = {},
            uiState = ClinicianDashboardUiState(
                avgFemaleScore = 53.0f,
                avgMaleScore = 65.0f,
                genAiInsights = listOf(
                    "Insight 1" to "Not suitable for localization if the string is user-facing. 3. Building the String Dynamically (If parts of it could change) This is not the case for your specific string.",
                    "Insight 2" to "Content 2",
                    "Insight 3" to "Content 3"
                ),
                insightsLoadingState = LoadingState.Success
            )
        )
    }

}