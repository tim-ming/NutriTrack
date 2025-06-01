package com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.fruit

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.fit2081.nutritrack_timming_32619138.commonUI.icons.Search
import com.fit2081.nutritrack_timming_32619138.commonUI.modifier.shimmerLoading
import com.fit2081.nutritrack_timming_32619138.data.nutriCoachTips.NutriCoachTip
import com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.tip.TipUiState
import com.fit2081.nutritrack_timming_32619138.state.LoadingState
import com.fit2081.nutritrack_timming_32619138.ui.theme.AccentDark
import com.fit2081.nutritrack_timming_32619138.ui.theme.NutriTrackTheme
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary100
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary200
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary400
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary600
import java.time.LocalDateTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColumnScope.FruitSection(
    fruitUiState: FruitUiState,
    onActiveChange: (Boolean) -> Unit,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    onSuggestionClick: (String) -> Unit,
    onSearch: (String) -> Unit,
    focusManager: FocusManager,
    focusRequester: FocusRequester
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        if (!fruitUiState.isOptimal) {
            NutriTrackFruit(
                fruitUiState = fruitUiState,
                onActiveChange = onActiveChange,
                onQueryChange = onQueryChange,
                onClear = onClear,
                onSuggestionClick = onSuggestionClick,
                onSearch = onSearch,
                focusManager = focusManager,
                focusRequester = focusRequester
            )
        } else {
            Box(modifier=Modifier.fillMaxSize()) {
                AsyncImage(
                    model = "https://picsum.photos/seed/${fruitUiState.hashCode()}/300",
                    contentDescription = "Random Image",
                    modifier = Modifier
                        .size(300.dp)
                        .clip(RoundedCornerShape(12.dp)).align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun NutriTrackFruit(
    fruitUiState: FruitUiState,
    onActiveChange: (Boolean) -> Unit,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    onSuggestionClick: (String) -> Unit,
    onSearch: (String) -> Unit,
    focusManager: FocusManager,
    focusRequester: FocusRequester
) {
    FruitSearchBar(
        query = fruitUiState.query,
        active = fruitUiState.active,
        onQueryChange = onQueryChange,
        onClear = onClear,
        onSuggestionClick = onSuggestionClick,
        onSearch = onSearch,
        onActiveChange = onActiveChange,
        suggestions = fruitUiState.suggestions,
        focusRequester = focusRequester,
        searchedBefore = fruitUiState.searchedBefore,
        loadingState = fruitUiState.loadingState
    )
    Spacer(modifier = Modifier.size(16.dp))
    when (fruitUiState.searchedBefore) {
        false -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Search a fruit to get started.",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.size(16.dp))
                AnimatedContent(
                    targetState = fruitUiState.loadingState,
                    transitionSpec = {
                        (fadeIn(animationSpec = tween(300)) + slideInVertically(
                            initialOffsetY = { 40 }
                        )).togetherWith(
                            fadeOut(
                                animationSpec = tween(
                                    200
                                )
                            )
                        )
                    },
                    label = "Fruit Initial State AnimatedContent"
                ) { state ->
                    when (state) {
                        is LoadingState.Loading -> {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                repeat(2) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        repeat(3) {
                                            Box(
                                                modifier = Modifier
                                                    .width(80.dp)
                                                    .height(32.dp)
                                                    .clip(CircleShape)
                                                    .shimmerLoading()
                                            )
                                        }

                                    }
                                }
                            }
                        }

                        is LoadingState.Error -> {
                            Text(text = state.message)
                        }

                        is LoadingState.Success -> {
                            FlowRow(
                                maxItemsInEachRow = 3,
                                horizontalArrangement = Arrangement.spacedBy(
                                    8.dp,
                                    alignment = Alignment.CenterHorizontally
                                ),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                fruitUiState.chipSuggestions.forEach { suggestion ->
                                    SuggestionChip(
                                        suggestion = suggestion,
                                        onClick = {
                                            onSuggestionClick(suggestion)
                                            onActiveChange(false)
                                            onQueryChange(suggestion)
                                        }
                                    )
                                }
                            }
                        }

                        else -> {}
                    }
                }
                Spacer(modifier = Modifier.size(32.dp))
                Text(
                    "Learn about a fruit's macronutrients,\nfamily, order and genus.",
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    color = Primary600
                )
            }


        }

        else -> {
            AnimatedContent(
                targetState = fruitUiState.loadingState,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(300))).togetherWith(fadeOut(
                        animationSpec = tween(
                            200
                        )
                    ) + slideOutVertically(
                        targetOffsetY = { -40 }
                    ))
                },
                label = "Fruit State AnimatedContent"
            ) { state ->
                when (state) {
                    is LoadingState.Loading -> {
                        FruitDetailsShimmer()
                    }

                    is LoadingState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (state).message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Red
                            )
                        }
                    }

                    is LoadingState.Success -> {
                        if (fruitUiState.fruit != null) {

                            Column {
                                val fruit = fruitUiState.fruit

                                Text(
                                    text = fruit.name,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Primary100)
                                ) {
                                    Column {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    Brush.horizontalGradient(
                                                        listOf(
                                                            AccentDark,
                                                            Primary600,
                                                        )
                                                    ), RoundedCornerShape(8.dp)
                                                )
                                                .padding(vertical = 8.dp, horizontal = 12.dp),
                                            verticalAlignment = Alignment.Bottom
                                        ) {
                                            Text(
                                                text = "${fruit.nutritions.calories} kcal",
                                                style = MaterialTheme.typography.titleMedium,
                                                color = Color.White
                                            )
                                            Text(
                                                text = " / 100g",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = Primary200
                                            )
                                        }
                                        Column(modifier = Modifier.padding(12.dp)) {

                                            FlowRow(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                NutrientItem(
                                                    "Sugar/Carbs",
                                                    "${fruit.nutritions.sugar}/${fruit.nutritions.carbohydrates}g"
                                                )
                                                NutrientItem(
                                                    "Protein",
                                                    "${fruit.nutritions.protein}g"
                                                )
                                                NutrientItem("Fat", "${fruit.nutritions.fat}g")
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Primary100)
                                ) {

                                    Column {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    Brush.horizontalGradient(
                                                        listOf(
                                                            AccentDark,
                                                            Primary600,
                                                        )
                                                    ), RoundedCornerShape(8.dp)
                                                )
                                                .padding(vertical = 8.dp, horizontal = 12.dp)
                                        ) {
                                            Text(
                                                text = "Classification",
                                                style = MaterialTheme.typography.titleMedium,
                                                color = Color.White
                                            )
                                        }
                                        Column(modifier = Modifier.padding(12.dp)) {

                                            FlowRow(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                ClassificationItem("Genus", fruit.genus)
                                                ClassificationItem("Family", fruit.family)
                                                ClassificationItem("Order", fruit.order)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }

                    else -> {}
                }

            }
        }
    }
}

@Composable
fun FruitDetailsShimmer() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(32.dp)
                .shimmerLoading()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .shimmerLoading()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .shimmerLoading()
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(24.dp)
                .shimmerLoading()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .shimmerLoading()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FruitSearchBar(
    query: String,
    active: Boolean,
    searchedBefore: Boolean,
    loadingState: LoadingState,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onSuggestionClick: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onClear: () -> Unit,
    suggestions: List<String>,
    focusRequester: FocusRequester
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    DockedSearchBar(
        leadingIcon = {
            Icon(
                imageVector = Search,
                contentDescription = "Search",
                modifier = Modifier.size(20.dp)
            )
        },
        trailingIcon = {
            IconButton(
                onClick = onClear
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        placeholder = {
            Text(text = "Search a fruit (e.g. Banana)")
        },
        query = query,
        onQueryChange = {
            onQueryChange(it)
        },
        onSearch = {
            onSearch(query)
            keyboardController?.hide()
            focusRequester.freeFocus()
            onActiveChange(false)
        },
        onActiveChange = {
            onActiveChange(it)
        },
        active = active,
        modifier = Modifier.focusRequester(focusRequester)
    ) {
        if (suggestions.isEmpty() && query.isNotEmpty()) {
            Text(
                text = "No matching fruits found",
                modifier = Modifier.padding(16.dp)
            )
        } else if (!searchedBefore && loadingState is LoadingState.Loading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Loading suggestions...",
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            LazyColumn {
                items(suggestions.size) { suggestion ->
                    val s = suggestions[suggestion]
                    ListItem(
                        headlineContent = {
                            // Bold the non-query parts in the suggestion
                            val startIndex = s.indexOf(query, ignoreCase = true)
                            val endIndex = startIndex + query.length

                            val annotatedText = buildAnnotatedString {
                                if (startIndex >= 0) {
                                    append(s.substring(0, startIndex))
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                        append(s.substring(startIndex, endIndex))
                                    }
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                        append(s.substring(endIndex))
                                    }
                                } else {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                        append(s)
                                    }
                                }
                            }

                            Text(
                                text = annotatedText,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },

                        modifier = Modifier
                            .clickable {
                                onSuggestionClick(s)
                                onQueryChange(s)
                                keyboardController?.hide()
                                focusRequester.freeFocus()
                                onActiveChange(false)
                            }
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SuggestionChip(
    suggestion: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Transparent)
            .border(
                width = 1.dp,
                color = Primary400,
                shape = CircleShape
            )
            .clickable(onClick = onClick)
            .padding(8.dp)
            .padding(horizontal = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ) {
            Text(
                text = suggestion,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                color = Primary600,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowScope.NutrientItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowScope.ClassificationItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium
        )
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}


@Preview
@Composable
fun NutriCoachContentPreview() {
    NutriTrackTheme {
        val sampleFruitUiState = FruitUiState(
            isOptimal = false,
            "Banana",
            listOf("Banana", "Orange", "Honeydew", "Dragon Fruit", "Avocado"),
            chipSuggestions = listOf("Apple", "Banana", "Orange", "Kiwi", "Mango", "Pineapple"),
            fruit = Fruit(
                name = "Banana",
                id = 1,
                family = "Musaceae",
                genus = "Musa",
                order = "Zingiberales",
                nutritions = Nutritions(
                    carbohydrates = 23.0,
                    protein = 1.3,
                    fat = 0.4,
                    calories = 96,
                    sugar = 17.0
                )
            ),
            active = true
        )

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
            messageLoadingState = LoadingState.Success
        )
        Column {
            FruitSection(
                fruitUiState = sampleFruitUiState,
                onActiveChange = {},
                onQueryChange = {},
                onClear = {},
                onSuggestionClick = {},
                onSearch = {},
                focusManager = LocalFocusManager.current,
                focusRequester = FocusRequester()
            )
        }
    }
}