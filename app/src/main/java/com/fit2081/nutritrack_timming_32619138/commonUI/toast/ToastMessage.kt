package com.fit2081.nutritrack_timming_32619138.commonUI.toast

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fit2081.nutritrack_timming_32619138.ui.theme.Accent
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary100
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary500
import kotlinx.coroutines.delay

@Composable
fun ToastMessage() {
    val toastMessage by ToastManager.toastMessage.collectAsState()
    val showMessage by ToastManager.showMessage.collectAsState()
    val isSuccess by ToastManager.isSuccess.collectAsState()

    if (showMessage) {
        LaunchedEffect(showMessage) {
            delay(2000)
            ToastManager.hideToast()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 64.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            visible = showMessage,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }),
        ) {
            ToastContent(toastMessage, isSuccess)
        }
    }
}

@Composable
fun ToastContent(
    toastMessage: String,
    isSuccess: Boolean,
) {
    val backgroundColor = Color(0xFFFFFFFF)
    val iconColor = if (isSuccess) Accent else MaterialTheme.colorScheme.error
    val icon = if (isSuccess) Icons.Default.CheckCircle else Icons.Default.Close
    val contentDescription = if (isSuccess) "Success icon" else "Error icon"

    Row(
        modifier = Modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp),
                clip = false,
                ambientColor = Primary100, spotColor = Primary500
            )
            .background(backgroundColor, shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconColor,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(24.dp)
        )
        Text(
            text = toastMessage,
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge.copy(letterSpacing = 0.sp)
        )
    }

}


@Preview
@Composable
fun PreviewCustomToast() {
    ToastContent(
        toastMessage = "Updated successfully.",
        isSuccess = true,
    )
}