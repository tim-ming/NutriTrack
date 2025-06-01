package com.fit2081.nutritrack_timming_32619138.screens.nutriCoach


import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fit2081.nutritrack_timming_32619138.data.nutriCoachTips.NutriCoachTip
import com.fit2081.nutritrack_timming_32619138.nav.Navigator
import com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.fruit.Fruit
import com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.fruit.FruitSection
import com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.fruit.FruitUiState
import com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.fruit.FruitViewModel
import com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.fruit.Nutritions
import com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.tip.TipHistoryDialog
import com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.tip.TipSection
import com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.tip.TipUiState
import com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.tip.TipViewModel
import com.fit2081.nutritrack_timming_32619138.state.LoadingState
import com.fit2081.nutritrack_timming_32619138.ui.theme.NutriTrackTheme
import java.time.LocalDateTime

@Composable
fun NutriCoachScreen(
    innerPadding: PaddingValues,
    navigator: Navigator,
    fruitViewModel: FruitViewModel,
    tipViewModel: TipViewModel
) {
    val fruitUiState by fruitViewModel.uiState.collectAsStateWithLifecycle()
    val tipUiState by tipViewModel.uiState.collectAsStateWithLifecycle()

    NutriCoachContent(
        fruitUiState = fruitUiState,
        tipUiState = tipUiState,
        onActiveChange = fruitViewModel::onActiveChange,
        onQueryChange = fruitViewModel::onQueryChange,
        onClear = fruitViewModel::onClearQuery,
        onSuggestionClick = fruitViewModel::onSuggestionSelect,
        onPrompt = tipViewModel::fetchAndProcessTip,
        onDialogVisibleChange = tipViewModel::onDialogVisibleChange,
        onSearch = fruitViewModel::searchFruit,
        innerPadding = innerPadding
    )
}

@Composable
fun NutriCoachContent(
    innerPadding: PaddingValues,
    fruitUiState: FruitUiState,
    tipUiState: TipUiState,
    onActiveChange: (Boolean) -> Unit,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    onSearch: (String) -> Unit,
    onSuggestionClick: (String) -> Unit,
    onPrompt: () -> Unit,
    onDialogVisibleChange: (Boolean) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(8.dp)
            .pointerInput(fruitUiState.active) {
                if (fruitUiState.active) {
                    detectTapGestures {
                        // Called on any tap outside focused element
                        onActiveChange(false)
                        focusManager.clearFocus()
                    }
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            FruitSection(
                fruitUiState,
                onActiveChange,
                onQueryChange,
                onClear,
                onSuggestionClick,
                onSearch,
                focusManager,
                focusRequester
            )
            Spacer(modifier = Modifier.size(16.dp))
            TipSection(
                tipUiState,
                onPrompt,
                onDialogVisibleChange
            )
        }
    }
}

@Preview
@Composable
fun NutriCoachContentPreview() {
    NutriTrackTheme {
        val sampleFruitUiState = FruitUiState(
            isOptimal = true,
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

        NutriCoachContent(
            fruitUiState = sampleFruitUiState,
            tipUiState = sampleTipUiState,
            onQueryChange = {},
            onClear = {},
            onSuggestionClick = {},
            onActiveChange = {},
            onPrompt = {},
            onDialogVisibleChange = {},
            onSearch = {},
            innerPadding = PaddingValues(0.dp),
        )
    }
}

@Preview
@Composable
fun TipHistoryDialogPreview() {
    NutriTrackTheme {
        val sampleTips = listOf(
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
            ),
            NutriCoachTip(
                tipId = 3,
                userId = "user123",
                message = "Try to limit processed foods and focus on whole, natural ingredients for better nutrition.",
                time = LocalDateTime.now(),
                prompt = "This is a prompt"
            ),
            NutriCoachTip(
                tipId = 4,
                userId = "user123",
                message = "Include protein in every meal to help maintain muscle mass and keep you feeling satisfied.",
                time = LocalDateTime.now(),
                prompt = "This is a prompt"
            )
        )

        TipHistoryDialog(
            tipHistory = sampleTips,
            onDismiss = {}
        )
    }
}

