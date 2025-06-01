package com.fit2081.nutritrack_timming_32619138.screens.questionnaire

import PersonaType
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SaveAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fit2081.nutritrack_timming_32619138.commonUI.TopBackButton
import com.fit2081.nutritrack_timming_32619138.nav.NavigationEventHandler
import com.fit2081.nutritrack_timming_32619138.nav.Navigator
import com.fit2081.nutritrack_timming_32619138.screens.questionnaire.components.FoodCategories
import com.fit2081.nutritrack_timming_32619138.screens.questionnaire.components.PersonaSection
import com.fit2081.nutritrack_timming_32619138.screens.questionnaire.components.TimingsSection
import com.fit2081.nutritrack_timming_32619138.ui.theme.Accent
import com.fit2081.nutritrack_timming_32619138.ui.theme.NutriTrackTheme
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary200
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary400
import java.time.LocalDateTime

@Composable
fun QuestionnaireScreen(
    innerPadding: PaddingValues,
    navigator: Navigator,
    viewModel: QuestionnaireViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()

    NavigationEventHandler(
        navigationEvent = navigationEvent,
        navigator = navigator,
        onNavigationHandled = viewModel::onNavigationHandled
    )

    QuestionnaireContent(
        innerPadding = innerPadding,
        selectedFoodCategories = uiState.foodCategories,
        onFoodCategorySelected = { viewModel.toggleFoodCategory(it) },
        selectedPersona = uiState.persona,
        onPersonaSelected = { viewModel.selectPersona(it) },
        timings = uiState.timings,
        onTimingsChange = { i, t -> viewModel.updateTiming(i, t) },
        onSubmit = viewModel::onSubmit,
        canSubmit = uiState.canSubmit,
        onBack = viewModel::onBack
    )
}

@Composable
fun QuestionnaireContent(
    innerPadding: PaddingValues,
    selectedFoodCategories: Set<String>,
    onFoodCategorySelected: (String) -> Unit,
    selectedPersona: PersonaType?,
    onPersonaSelected: (PersonaType) -> Unit,
    timings: List<Timing>,
    onTimingsChange: (Int, LocalDateTime) -> Unit,
    onSubmit: () -> Unit,
    canSubmit: Boolean,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp)
            .padding(top = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Header(onBack)

        HorizontalDivider(modifier = Modifier.padding(vertical = 36.dp), color = Primary200)

        FoodCategories(selectedFoodCategories, onFoodCategorySelected)

        HorizontalDivider(modifier = Modifier.padding(vertical = 36.dp), color = Primary200)

        PersonaSection(selectedPersona, onPersonaSelected)

        HorizontalDivider(modifier = Modifier.padding(vertical = 36.dp), color = Primary200)

        TimingsSection(timings, onTimingsChange)

        Spacer(modifier = Modifier.height(32.dp))

        SubmitButton(onSubmit, canSubmit)

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun Header(onBack: () -> Unit) {
    TopBackButton(onBack, "Home")
    Spacer(modifier = Modifier.height(24.dp))
    Text(
        "Dietary survey",
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Medium
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        "To help us better understand you, please fill out the survey on your daily dietary habits.",
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
fun SubmitButton(onSubmit: () -> Unit, canSubmit: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Button(
            onClick = onSubmit,
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (canSubmit) Accent else Primary200
            ),
            contentPadding = PaddingValues(
                start = 8.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.SaveAlt,
                contentDescription = "Back to Login",
                tint = if (canSubmit) Color.White else Primary400
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Save",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = if (canSubmit) Color.White else Primary400
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewQuestionnaireScreen() {
    NutriTrackTheme {
        QuestionnaireContent(innerPadding = PaddingValues(0.dp),
            selectedFoodCategories = setOf(FOOD_CATEGORIES[0]),
            onFoodCategorySelected = { _ -> },
            selectedPersona = PersonaType.FOOD_CAREFREE,
            onPersonaSelected = {},
            onTimingsChange = { _, _ -> },
            timings = listOf(),
            canSubmit = false,
            onSubmit = { },
            onBack = {})
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewPersonaDetailModal() {
//    NutriTrackTheme {
//        PersonaDetailModal(
//            persona = PersonaType.FOOD_CAREFREE,
//            onDismiss = {},
//            onSelect = {}
//        )
//    }
//}