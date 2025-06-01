package com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.tip

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fit2081.nutritrack_timming_32619138.commonUI.TypewriterTextEffect
import com.fit2081.nutritrack_timming_32619138.commonUI.icons.History
import com.fit2081.nutritrack_timming_32619138.commonUI.icons.Stars
import com.fit2081.nutritrack_timming_32619138.commonUI.modifier.shimmerLoading
import com.fit2081.nutritrack_timming_32619138.data.nutriCoachTips.NutriCoachTip
import com.fit2081.nutritrack_timming_32619138.state.LoadingState
import com.fit2081.nutritrack_timming_32619138.ui.theme.Accent
import com.fit2081.nutritrack_timming_32619138.ui.theme.AccentDark
import com.fit2081.nutritrack_timming_32619138.ui.theme.NutriTrackTheme
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary100
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary200
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary500
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary600
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary800
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ColumnScope.TipSection(
    tipUiState: TipUiState,
    onPrompt: () -> Unit,
    onDialogVisibleChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Text(
                        text = "NutriTrack AI",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
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
                IconButton(
                    onClick = { onDialogVisibleChange(true) },
                    modifier = Modifier
                        .shadow(
                            elevation = 8.dp,
                            shape = CircleShape,
                            ambientColor = Primary100, spotColor = Primary500
                        )
                        .size(48.dp)
                        .background(Color.White)
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = History,
                        contentDescription = "History",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            AnimatedContent(
                targetState = tipUiState.promptedBefore,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(300)) + slideInVertically(
                        initialOffsetY = { 40 }
                    )).togetherWith(fadeOut(animationSpec = tween(200)) + slideOutVertically(
                        targetOffsetY = { -40 }
                    ))
                },
                label = "Fruit Initial State AnimatedContent"
            ) { promptedBefore ->
                if (!promptedBefore) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text("Improve your Nutrition with AI-analyzed tips")
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 32.dp, vertical = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = onPrompt,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .clip(RoundedCornerShape(24.dp)),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                ),
                                contentPadding = PaddingValues()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .background(
                                            brush =
                                            Brush.horizontalGradient(
                                                listOf(
                                                    AccentDark,
                                                    Accent
                                                )
                                            ),
                                            shape = RoundedCornerShape(24.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Get started",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.White,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Spacer(modifier = Modifier.size(8.dp))
                                        Icon(
                                            imageVector = Stars,
                                            contentDescription = "AI Stars",
                                            tint = Color.White,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Primary100)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                            ) {
                                AnimatedContent(
                                    targetState = tipUiState.messageLoadingState,
                                    transitionSpec = {
                                        (fadeIn(animationSpec = tween(300))).togetherWith(fadeOut(
                                            animationSpec = tween(200)
                                        ) + slideOutVertically(
                                            targetOffsetY = { -40 }
                                        ))
                                    },
                                    label = "Tip State AnimatedContent"
                                ) { state ->
                                    when (state) {
                                        is LoadingState.Loading -> {
                                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(18.dp)
                                                        .clip(RoundedCornerShape(8.dp))
                                                        .shimmerLoading()
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.95f)
                                                        .height(18.dp)
                                                        .clip(RoundedCornerShape(8.dp))
                                                        .shimmerLoading()
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(18.dp)
                                                        .clip(RoundedCornerShape(8.dp))
                                                        .shimmerLoading()
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.9f)
                                                        .height(18.dp)
                                                        .clip(RoundedCornerShape(8.dp))
                                                        .shimmerLoading()
                                                )
                                            }
                                        }

                                        is LoadingState.Error -> {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Icon(
                                                    imageVector = Icons.Default.Close,
                                                    contentDescription = "Error",
                                                    modifier = Modifier.size(48.dp),
                                                    tint = Color.Red
                                                )
                                                Spacer(modifier = Modifier.height(8.dp))
                                                Text(
                                                    text = state.message,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.Red
                                                )
                                            }
                                        }

                                        is LoadingState.Success -> {
                                            TypewriterTextEffect(tipUiState.message) { text ->
                                                Text(
                                                    text = text,
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    textAlign = TextAlign.Start,
                                                    color = Primary800
                                                )
                                            }
                                        }

                                        else -> {}
                                    }
                                }
                            }
                        }

                    }
                }
            }

        }

        AnimatedContent(
            targetState = tipUiState.promptedBefore,
            transitionSpec = {
                (fadeIn(animationSpec = tween(300)) + slideInVertically(
                    initialOffsetY = { 40 }
                )).togetherWith(fadeOut(animationSpec = tween(200)) + slideOutVertically(
                    targetOffsetY = { -40 }
                ))
            },
            label = "New Tip Button AnimatedContent",
            modifier = Modifier.align(Alignment.BottomCenter)
        ) { show ->
            if (show) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val isLoading = LoadingState.Loading != tipUiState.messageLoadingState
                    Button(
                        onClick = onPrompt,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .clip(RoundedCornerShape(24.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                        ),
                        enabled = isLoading,
                        contentPadding = PaddingValues()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .background(
                                    brush = if (isLoading) {
                                        Brush.horizontalGradient(
                                            listOf(
                                                AccentDark,
                                                Accent
                                            )
                                        )
                                    } else {
                                        SolidColor(Primary200)
                                    },
                                    shape = RoundedCornerShape(24.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "New Tip",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Icon(
                                    imageVector = Stars,
                                    contentDescription = "AI Stars",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (tipUiState.dialogOpen) {
        TipHistoryDialog(
            tipHistory = tipUiState.tipHistory.sortedBy {
                it.time
            }.asReversed(),
            onDismiss = { onDialogVisibleChange(false) }
        )
    }
}

@Composable
fun TipHistoryDialog(
    tipHistory: List<NutriCoachTip>,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nutrition Tip History",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Dialog",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onDismiss() }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (tipHistory.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No tips yet.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(tipHistory) { tip ->
                            TipHistoryItem(
                                tip = tip,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary600
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Close")
                }
            }
        }
    }
}

@Composable
fun TipHistoryItem(
    tip: NutriCoachTip,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Primary100)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = tip.message,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = tip.time.format(
                    DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())
                ),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun TipSectionPreview() {
    NutriTrackTheme {
        val sampleTipUiState = TipUiState(
            message = "Try to include more whole grains in your diet. They provide essential fiber and nutrients for better digestive health.",
            tipHistory = listOf(
                NutriCoachTip(
                    tipId = 1,
                    userId = "user123",
                    message = "Adding colorful vegetables to your meals increases your intake of important vitamins and antioxidants.",
                    time = LocalDateTime.now(),
                    prompt = "This is a prompt"
                ),
                NutriCoachTip(
                    tipId = 2,
                    userId = "user123",
                    message = "Drinking water before meals can help you feel fuller and prevent overeating.",
                    time = LocalDateTime.now(),
                    prompt = "This is a prompt"
                )
            ),
            messageLoadingState = LoadingState.Loading
        )
        Column() {

            TipSection(
                tipUiState = sampleTipUiState,
                onPrompt = {},
                onDialogVisibleChange = {}
            )
        }
    }
}