package com.fit2081.nutritrack_timming_32619138.commonUI.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Exclamation: ImageVector
    get() {
        if (_Exclamation != null) {
            return _Exclamation!!
        }
        _Exclamation = ImageVector.Builder(
            name = "ExclamationCircle",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF0F172A)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.5f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12f, 9f)
                verticalLineTo(12.75f)
                moveTo(21f, 12f)
                curveTo(21f, 16.9706f, 16.9706f, 21f, 12f, 21f)
                curveTo(7.0294f, 21f, 3f, 16.9706f, 3f, 12f)
                curveTo(3f, 7.0294f, 7.0294f, 3f, 12f, 3f)
                curveTo(16.9706f, 3f, 21f, 7.0294f, 21f, 12f)
                close()
                moveTo(12f, 15.75f)
                horizontalLineTo(12.0075f)
                verticalLineTo(15.7575f)
                horizontalLineTo(12f)
                verticalLineTo(15.75f)
                close()
            }
        }.build()
        return _Exclamation!!
    }

private var _Exclamation: ImageVector? = null
