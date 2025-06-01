package com.fit2081.nutritrack_timming_32619138.screens.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fit2081.nutritrack_timming_32619138.R
import com.fit2081.nutritrack_timming_32619138.data.patient.Patient
import com.fit2081.nutritrack_timming_32619138.data.patient.PatientStats
import com.fit2081.nutritrack_timming_32619138.nav.NavigationEventHandler
import com.fit2081.nutritrack_timming_32619138.nav.Navigator
import com.fit2081.nutritrack_timming_32619138.screens.insights.getProgressColor
import com.fit2081.nutritrack_timming_32619138.state.StateHandler
import com.fit2081.nutritrack_timming_32619138.ui.theme.Accent
import com.fit2081.nutritrack_timming_32619138.ui.theme.AccentDark
import com.fit2081.nutritrack_timming_32619138.ui.theme.AccentLight
import com.fit2081.nutritrack_timming_32619138.ui.theme.NutriTrackTheme
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary100
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary200
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary400
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary500
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary600

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    navigator: Navigator,
    viewModel: HomeViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val loadingState by viewModel.loadingState.collectAsStateWithLifecycle()

    val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()

    NavigationEventHandler(
        navigationEvent = navigationEvent,
        navigator = navigator,
        onNavigationHandled = viewModel::onNavigationHandled
    )

    StateHandler(state = loadingState, navigator = navigator, onSuccess = {
        HomeContent(
            innerPadding = innerPadding,
            state = uiState,
            onEnhanceDietClick = { viewModel.onEnhanceDietClicked() },
            onUpdateSurveyClick = { viewModel.onQuestionnaireClicked() },
            onSeeAllScores = { viewModel.onSeeAllScoresClicked() }
        )
    })
}

@Composable
fun HomeContent(
    innerPadding: PaddingValues,
    state: HomeUiState,
    onUpdateSurveyClick: () -> Unit,
    onSeeAllScores: () -> Unit,
    onEnhanceDietClick: () -> Unit
) {
    // Animations (staggered!)
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    val animationDuration = 600
    val staggerDelay = 100

    val performanceCardOffset by animateIntOffsetAsState(
        targetValue = if (isVisible) IntOffset.Zero else IntOffset(0, 100),
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = 0,
            easing = FastOutSlowInEasing
        )
    )

    val performanceCardAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = 0,
            easing = FastOutSlowInEasing
        )
    )

    val updateSurveyCardOffset by animateIntOffsetAsState(
        targetValue = if (isVisible) IntOffset.Zero else IntOffset(0, 100),
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = staggerDelay,
            easing = FastOutSlowInEasing
        )
    )

    val updateSurveyCardAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = staggerDelay,
            easing = FastOutSlowInEasing
        )
    )

    val enhanceDietCardOffset by animateIntOffsetAsState(
        targetValue = if (isVisible) IntOffset.Zero else IntOffset(0, 100),
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = staggerDelay * 2,
            easing = FastOutSlowInEasing
        )
    )

    val enhanceDietCardAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = staggerDelay * 2,
            easing = FastOutSlowInEasing
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Hello,",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Primary400
                )
                Text(
                    text = state.patient.name + ".",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .offset { performanceCardOffset }
                .alpha(performanceCardAlpha)
        ) {
            PerformanceCard(
                state = state,
                onSeeAllScores = onSeeAllScores
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .offset { updateSurveyCardOffset }
                .alpha(updateSurveyCardAlpha)
        ) {
            UpdateSurveyCard(
                onUpdateSurveyClick = onUpdateSurveyClick,
                hasResponded = state.hasRespondedSurvey
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .offset { enhanceDietCardOffset }
                .alpha(enhanceDietCardAlpha)
        ) {
            EnhanceDietCard(
                onEnhanceDietClick = onEnhanceDietClick
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        HorizontalDivider(color = Primary200)

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "What is the Food Quality Score?",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your Food Quality Score provides a snapshot of how well your eating patterns align with established food guidelines, helping you identify both strengths and opportunities for improvement in your diet.\n\nThis personalized measurement considers various food groups including vegetables, fruits, whole grains, and proteins to give you practical insights for making healthier food choices.",
                style = MaterialTheme.typography.bodyMedium,
                color = Primary600
            )
        }
    }
}

@Composable
fun PerformanceCard(
    state: HomeUiState,
    onSeeAllScores: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = Primary200,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onSeeAllScores()
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Performance",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "To Insights",
                tint = Primary600,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = "Overall food quality score",
            style = MaterialTheme.typography.bodyMedium,
            color = Primary600
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                TotalScoreCircularIndicator(
                    score = state.patient.heifaTotalScore,
                    maxScore = 100.0f,
                    label = "/100"
                )
            Spacer(modifier = Modifier.height(24.dp))
            NutritionCategoryCircularStats(
                stats = PatientStats.fromPatient(state.patient),
            )
        }
    }
}

@Composable
fun NutritionCategoryCircularStats(
    stats: PatientStats,
    maxScore: Float = 10f
) {
    val averageStats = stats.getSubcategoryAverages()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        averageStats.forEach { stat ->
            AnimatedCircularProgress(label = stat.name, score = stat.score, maxScore = maxScore)
        }
    }
}


@Composable
fun AnimatedCircularProgress(
    score: Float,
    maxScore: Float = 10.0f,
    size: Dp = 70.dp,
    strokeWidth: Dp = 4.dp,
    animationDuration: Int = 1000,
    backgroundColor: Color = Primary200,
    label: String = ""
) {
    val progress = score / maxScore

    var animatedScore by rememberSaveable { mutableFloatStateOf(0.0f) }

    var animatedProgress by rememberSaveable { mutableFloatStateOf(0.0f) }

    val progressColor = getProgressColor(animatedProgress)

    LaunchedEffect(score) {
        animate(
            initialValue = 0.0f,
            targetValue = score,
            animationSpec = tween(durationMillis = animationDuration, easing = FastOutSlowInEasing)
        ) { value, _ -> animatedScore = value }
    }

    LaunchedEffect(progress) {
        animate(
            initialValue = 0.0f,
            targetValue = progress,
            animationSpec = tween(durationMillis = animationDuration, easing = FastOutSlowInEasing)
        ) { value, _ -> animatedProgress = value }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.size(size + strokeWidth),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = backgroundColor,
                    radius = (size.toPx()) / 2,
                    style = Stroke(width = strokeWidth.toPx())
                )
            }

            Canvas(modifier = Modifier.size(size)) {
                val sweepAngle = 360f * animatedProgress
                drawArc(
                    color = progressColor,
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    ),
                )
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = String.format("%.1f", animatedScore),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = progressColor
                )
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Primary600
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
fun UpdateSurveyCard(
    onUpdateSurveyClick: () -> Unit,
    hasResponded: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onUpdateSurveyClick()
            }
    ) {
        Row(
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
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.note),
                contentDescription = "questionnaire",
                modifier = Modifier.size(56.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (hasResponded) "Update survey responses" else "Take nutrition survey",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (hasResponded) "For the most personalised advice, please update your Dietary Survey." else "For the most personalised advice, please take the Dietary Survey.",
                    style = MaterialTheme.typography.labelMedium.copy(color = Primary600),
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "To questionnaire",
                tint = Primary600,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun EnhanceDietCard(
    onEnhanceDietClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onEnhanceDietClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    8.dp,
                    RoundedCornerShape(12.dp),
                    ambientColor = Primary100,
                    spotColor = Primary500
                )
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Accent,
                            AccentDark,
                            Accent,
                            AccentLight,
                            AccentDark,
                            Accent,
                            AccentLight,
                        ),
                        start = Offset.Zero,
                        end = Offset.Infinite
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(2.dp)
                .background(
                    Color.White,
                    RoundedCornerShape(11.dp)
                )
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.chart),
                contentDescription = "questionnaire",
                modifier = Modifier.size(56.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Enhance your diet", style = MaterialTheme.typography.titleMedium,
                    color = AccentDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "NutriTrack AI provides personalised tips to improve your diet.",
                    style = MaterialTheme.typography.labelMedium.copy(color = Primary600),
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "To questionnaire",
                tint = AccentDark,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun TotalScoreCircularIndicator(
    score: Float,
    maxScore: Float = 100.0f,
    size: Dp = 200.dp,
    strokeWidth: Dp = 16.dp,
    backgroundColor: Color = Primary200,
    label: String = ""
) {
    val progress = score / maxScore

    var animatedScore by rememberSaveable { mutableFloatStateOf(0.0f) }

    var animatedProgress by rememberSaveable { mutableFloatStateOf(0.0f) }

    val progressColor = getProgressColor(animatedProgress)

    LaunchedEffect(score) {
        animate(
            initialValue = 0.0f,
            targetValue = score,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        ) { value, _ -> animatedScore = value }
    }

    LaunchedEffect(progress) {
        animate(
            initialValue = 0.0f,
            targetValue = progress,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        ) { value, _ -> animatedProgress = value }
    }

    Box(
        modifier = Modifier
            .size(size + strokeWidth)
            .background(
                Color.White,
                CircleShape
            ),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = backgroundColor,
                radius = (size.toPx()) / 2,
                style = Stroke(width = strokeWidth.toPx())
            )
        }

        Canvas(modifier = Modifier.size(size)) {
            val sweepAngle = 360f * animatedProgress
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round
                ),
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = String.format("%.2f", animatedScore),
                style = MaterialTheme.typography.displayMedium.copy(color = AccentDark),
                fontWeight = FontWeight.SemiBold,
                color = progressColor
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = Primary600
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFoodQualityProfileScreen() {
    NutriTrackTheme {
        HomeContent(
            innerPadding = PaddingValues(0.dp),
            onUpdateSurveyClick = {},
            state = HomeUiState(
                Patient.MOCK
            ),
            onEnhanceDietClick = {},
            onSeeAllScores = {}
        )
    }
}