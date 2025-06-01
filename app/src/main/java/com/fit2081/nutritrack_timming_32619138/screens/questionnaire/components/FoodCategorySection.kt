package com.fit2081.nutritrack_timming_32619138.screens.questionnaire.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fit2081.nutritrack_timming_32619138.screens.questionnaire.FOOD_CATEGORIES
import com.fit2081.nutritrack_timming_32619138.ui.theme.AccentDark
import com.fit2081.nutritrack_timming_32619138.ui.theme.AccentLight
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary200
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary600

@Composable
fun FoodCategories(
    selectedFoodCategories: Set<String>,
    onFoodCategorySelected: (String) -> Unit,
) {
    Text(
        "Food categories you eat",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Medium
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        "Select the food categories you ate today.",
        style = MaterialTheme.typography.bodyMedium,
        color = Primary600
    )

    Spacer(modifier = Modifier.height(8.dp))

    FoodCategoriesGrid(
        selectedFoodCategories = selectedFoodCategories,
        onFoodCategorySelected = onFoodCategorySelected,
    )
}

@Composable
fun FoodCategoriesGrid(
    selectedFoodCategories: Set<String>,
    onFoodCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy((-10).dp),
    ) {
        FOOD_CATEGORIES.forEachIndexed { _, foodCategory ->
            FoodCategoryChip(
                category = foodCategory,
                isSelected = foodCategory in selectedFoodCategories,
                onSelected = onFoodCategorySelected
            )
        }
    }
}

@Composable
fun FoodCategoryChip(
    category: String,
    isSelected: Boolean,
    onSelected: (String) -> Unit,
) {
    FilterChip(
        selected = isSelected,
        onClick = { onSelected(category) },
        label = {
            Text(category, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
        },
        shape = RoundedCornerShape(999.dp),
        leadingIcon = null,
        colors = FilterChipDefaults.filterChipColors(
//            selectedContainerColor = Accent,
//            selectedLabelColor = Color.White,
            selectedContainerColor = AccentLight, selectedLabelColor = AccentDark
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = isSelected,
            selectedBorderColor = AccentDark,
//            selectedBorderWidth = 1.dp,
            selectedBorderWidth = 1.5.dp,
            borderColor = Primary200,
            borderWidth = 1.dp,
        )
    )
}