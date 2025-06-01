package com.fit2081.nutritrack_timming_32619138.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fit2081.nutritrack_timming_32619138.R

val Poppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_light, FontWeight.Light)
)
val Satoshi = FontFamily(
    Font(R.font.satoshi_regular, FontWeight.Normal),
    Font(R.font.satoshi_medium, FontWeight.Medium),
    Font(R.font.satoshi_bold, FontWeight.Bold),
    Font(R.font.satoshi_light, FontWeight.Light)
)
private val defaultTypography = Typography()
val CustomTypography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = Satoshi, letterSpacing = -1.5.sp),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = Satoshi),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = Satoshi),

    headlineLarge = defaultTypography.headlineLarge.copy(fontSize = 36.sp, fontFamily = Satoshi, letterSpacing = -1.2.sp),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = Satoshi, letterSpacing = -0.7.sp),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = Satoshi, letterSpacing = -0.5.sp),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = Satoshi, letterSpacing = -0.4.sp),
    titleMedium = defaultTypography.titleMedium.copy(fontSize = 18.sp, fontFamily = Satoshi, letterSpacing = -0.3.sp),
    titleSmall = defaultTypography.titleSmall.copy(fontSize = 16.sp, fontFamily = Satoshi, letterSpacing = -0.2.sp),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = Satoshi, letterSpacing = 0.sp, lineHeight=20.sp),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = Satoshi, letterSpacing = 0.1.sp, lineHeight = 20.sp),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = Satoshi, letterSpacing = 0.sp),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = Satoshi, letterSpacing = 0.sp),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = Satoshi, fontSize = 14.sp, lineHeight = 18.sp, letterSpacing = 0.sp, fontWeight = FontWeight.Normal),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = Satoshi, letterSpacing = 0.sp),
)