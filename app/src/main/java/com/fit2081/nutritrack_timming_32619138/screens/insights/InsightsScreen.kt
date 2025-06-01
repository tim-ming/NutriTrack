package com.fit2081.nutritrack_timming_32619138.screens.insights

import android.content.Intent
import android.content.Intent.ACTION_SEND
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fit2081.nutritrack_timming_32619138.commonUI.icons.Stars
import com.fit2081.nutritrack_timming_32619138.data.patient.PatientStats
import com.fit2081.nutritrack_timming_32619138.data.patient.StatEntry
import com.fit2081.nutritrack_timming_32619138.nav.NavigationEventHandler
import com.fit2081.nutritrack_timming_32619138.nav.Navigator
import com.fit2081.nutritrack_timming_32619138.state.StateHandler
import com.fit2081.nutritrack_timming_32619138.ui.theme.Accent
import com.fit2081.nutritrack_timming_32619138.ui.theme.AccentDark
import com.fit2081.nutritrack_timming_32619138.ui.theme.AccentLight
import com.fit2081.nutritrack_timming_32619138.ui.theme.AccentLighter
import com.fit2081.nutritrack_timming_32619138.ui.theme.NutriTrackTheme
import com.fit2081.nutritrack_timming_32619138.ui.theme.Orange
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary100
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary200
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary500
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary600
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary800
import com.fit2081.nutritrack_timming_32619138.ui.theme.Red
import com.fit2081.nutritrack_timming_32619138.ui.theme.Yellow

@Composable
fun InsightsScreen(
    innerPadding: PaddingValues,
    navigator: Navigator,
    viewModel: InsightsViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val loadingState by viewModel.loadingState.collectAsStateWithLifecycle()
    val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()

    NavigationEventHandler(
        navigationEvent = navigationEvent,
        navigator = navigator,
        onNavigationHandled = viewModel::onNavigationHandled
    )

    StateHandler(
        state = loadingState,
        navigator = navigator,
        onSuccess = {
            InsightsContent(
                innerPadding, uiState,
                onImproveDietClick = viewModel::onImproveDietClick,
                onExpandedChange = viewModel::onExpandedChange
            )
        },
    )

}

@Composable
fun InsightsContent(
    innerPadding: PaddingValues,
    uiState: InsightsUiState,
    onExpandedChange: (Boolean) -> Unit,
    onImproveDietClick: () -> Unit
) {
    val context = LocalContext.current

    fun share() {
        val shareIntent = Intent(ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "Hi, I just got a HEIFA score of ${uiState.stats.heifaTotalScore.score}"
            )
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share your insights"))
    }
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                "Insights",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(32.dp))

            TotalScoreCard(
                uiState = uiState
            )

            Spacer(modifier = Modifier.height(16.dp))

            NutritionCategoryLinearStats(
                stats = uiState.stats.getCoreFoodStats(),
                title = "Core foods"
            )

            Spacer(modifier = Modifier.height(16.dp))

            NutritionCategoryCircularStats(
                stats = uiState.stats.getLimitStats(),
                title = "Discretionary Intake"
            )

            Spacer(modifier = Modifier.height(16.dp))

            NutritionCategoryCircularStats(
                stats = uiState.stats.getOtherStats(),
                title = "Others"
            )



            Spacer(modifier = Modifier.height(48.dp))
        }
        ExpandableActionFab(
            share = { share() },
            onImproveDietClick = onImproveDietClick,
            isExpanded = uiState.isExpanded,
            onExpandedChange = onExpandedChange
        )
    }
}

@Composable
fun TotalScoreCard(uiState: InsightsUiState){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
        Modifier
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(32.dp),
                clip = false,
                ambientColor = Primary100, spotColor = Primary500
            )
            .background(Color.White, shape = RoundedCornerShape(32.dp))
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Primary200,
                shape = RoundedCornerShape(32.dp)
            )
            .padding(16.dp)
            .padding(top = 8.dp)
    ) {
        Text(
            "Total Score",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Medium
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TotalScoreCircularIndicator(
                score = uiState.stats.heifaTotalScore.score,
                label = "/100"
            )
            Spacer(modifier = Modifier.height(16.dp))
            HeifaScoreMessage(
                score = uiState.stats.heifaTotalScore.score.toInt()
            )
        }
    }
}

// https://pmc.ncbi.nlm.nih.gov/articles/PMC8541309/#:~:text=3.2.%20S%2DMHEI’s%20Score%20and%20Interpretation
@Composable
fun HeifaScoreMessage(score: Int) {
    val title: String
    val message: String
    val color: Color

    when {
        score > 80 -> {
            title = "Excellent"
            message = "Great job! Your diet quality is excellent. Keep it up!"
            color = AccentDark
        }

        score in 51..80 -> {
            title = "Moderate"
            message = "You're doing okay, but there's room for improvement in your diet."
            color = Orange
        }

        else -> {
            title = "Poor"
            message = "Your diet quality is poor. Consider making healthier food choices."
            color = Red
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    HorizontalDivider(color = Primary200)
    Spacer(modifier = Modifier.height(16.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = color,
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}

@Composable
fun NutritionCategoryCircularStats(
    stats: List<StatEntry>,
    title: String,
    maxScore: Float = 10f
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(24.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            maxItemsInEachRow = 3
        ) {
            stats.forEach { stat ->
                AnimatedCircularProgress(label = stat.name, score = stat.score, maxScore = maxScore)
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}


@Composable
fun FlowRowScope.AnimatedCircularProgress(
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
        modifier = Modifier.weight(1f)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Primary600
        )
        Spacer(modifier = Modifier.height(4.dp))
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
    }
}

@Composable
fun BoxScope.ExpandableActionFab(
    share: () -> Unit,
    onImproveDietClick: () -> Unit,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 45f else 0f,
        animationSpec = tween(300),
        label = "rotation"
    )

    Column(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Bottom)
    ) {
        // Share FAB
        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn(animationSpec = tween(300, delayMillis = 50)) +
                    expandVertically(animationSpec = tween(300, delayMillis = 50)),
            exit = fadeOut(animationSpec = tween(200)) +
                    shrinkVertically(animationSpec = tween(200))
        ) {
            Button(
                onClick = {
                    share()
                    onExpandedChange(false)
                },
                modifier = Modifier
                    .height(54.dp)
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
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentLighter,
                    contentColor = Primary800,
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = "Share",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Share",
                    style = MaterialTheme.typography.titleMedium,
                    color = Primary800
                )
            }
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn(animationSpec = tween(300, delayMillis = 100)) +
                    expandVertically(animationSpec = tween(300, delayMillis = 100)),
            exit = fadeOut(animationSpec = tween(200)) +
                    shrinkVertically(animationSpec = tween(200))
        ) {
            Button(
                onClick = {
                    onImproveDietClick()
                },
                modifier = Modifier
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
                        AccentLighter,
                        RoundedCornerShape(11.dp)
                    )
                    .padding(horizontal = 14.dp, vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                ),
                contentPadding = PaddingValues()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Stars,
                        contentDescription = "AI Stars",
                        tint = AccentDark,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Improve Diet",
                        style = MaterialTheme.typography.titleMedium,
                        color = AccentDark
                    )
                }

            }
        }

        // Main FAB
        FloatingActionButton(
            onClick = { onExpandedChange(!isExpanded) },
            containerColor = Accent,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = if (isExpanded) "Close" else "Open",
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotationAngle)
            )
        }
    }
}

@Composable
fun NutritionCategoryLinearStats(
    modifier: Modifier = Modifier,
    stats: List<StatEntry>,
    title: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(24.dp))
        stats.forEach { stat ->
            StatRow(stat, 10.0f)
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun StatRow(
    stat: StatEntry,
    maxScore: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stat.name,
            style = MaterialTheme.typography.bodyLarge,
            color = Primary600,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "${stat.score}/$maxScore",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = Primary800,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }

    Spacer(Modifier.height(4.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val progress = stat.score / maxScore
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(1000.dp)),
            color = getProgressColor(progress),
            trackColor = Primary200,
        )
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
            .shadow(
                16.dp,
                CircleShape,
                ambientColor = Primary100,
                spotColor = Primary500
            )
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

fun getProgressColor(progress: Float): Color {
    return when {
        progress <= 0.25f -> {
            Red
        }

        progress <= 0.5f -> {
            Orange
        }

        progress <= 0.75f -> {
            Yellow
        }

        else -> {
            AccentDark
        }
    }
}


@Preview(showBackground = true)
@Composable
fun InsightsScreenPreview() {
    NutriTrackTheme {
        InsightsContent(
            innerPadding = PaddingValues(0.dp),
            uiState = InsightsUiState(
                PatientStats(
                    heifaTotalScore = StatEntry("Total score", 50.0f),
                    discretionaryHeifaScore = StatEntry("Discretionary", 5.5f),
                    vegetablesHeifaScore = StatEntry("Vegetables", 5.5f),
                    fruitHeifaScore = StatEntry("Fruit", 5.5f),
                    grainsAndCerealsHeifaScore = StatEntry("Grains and cereals", 5.5f),
                    wholeGrainsHeifaScore = StatEntry("Whole grains", 5.5f),
                    meatAndAlternativesHeifaScore = StatEntry("Meat and alternatives", 5.5f),
                    dairyAndAlternativesHeifaScore = StatEntry("Dairy and alternatives", 5.5f),
                    sodiumHeifaScore = StatEntry("Sodium", 5.5f),
                    alcoholHeifaScore = StatEntry("Alcohol", 5.5f),
                    waterHeifaScore = StatEntry("Water", 5.5f),
                    sugarHeifaScore = StatEntry("Sugar", 5.5f),
                    saturatedFatHeifaScore = StatEntry("Saturated fat", 5.5f),
                    unsaturatedFatHeifaScore = StatEntry("Unsaturated fat", 5.5f)
                )
            ),
            onExpandedChange = {},
            onImproveDietClick = {}
        )
    }
}