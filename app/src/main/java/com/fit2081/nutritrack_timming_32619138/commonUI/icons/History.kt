package com.fit2081.nutritrack_timming_32619138.commonUI.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val History: ImageVector
    get() {
        if (_History != null) {
            return _History!!
        }
        _History = ImageVector.Builder(
            name = "History",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(13.507f, 12.324f)
                arcToRelative(7f, 7f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.065f, -8.56f)
                arcTo(7f, 7f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2f, 4.393f)
                verticalLineTo(2f)
                horizontalLineTo(1f)
                verticalLineToRelative(3.5f)
                lineToRelative(0.5f, 0.5f)
                horizontalLineTo(5f)
                verticalLineTo(5f)
                horizontalLineTo(2.811f)
                arcToRelative(6.008f, 6.008f, 0f, isMoreThanHalf = true, isPositiveArc = true, -0.135f, 5.77f)
                lineToRelative(-0.887f, 0.462f)
                arcToRelative(7f, 7f, 0f, isMoreThanHalf = false, isPositiveArc = false, 11.718f, 1.092f)
                close()
                moveToRelative(-3.361f, -0.97f)
                lineToRelative(0.708f, -0.707f)
                lineTo(8f, 7.792f)
                verticalLineTo(4f)
                horizontalLineTo(7f)
                verticalLineToRelative(4f)
                lineToRelative(0.146f, 0.354f)
                lineToRelative(3f, 3f)
                close()
            }
        }.build()
        return _History!!
    }

private var _History: ImageVector? = null
