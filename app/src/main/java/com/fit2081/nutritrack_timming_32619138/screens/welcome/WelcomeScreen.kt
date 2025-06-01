package com.fit2081.nutritrack_timming_32619138.screens.welcome

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fit2081.nutritrack_timming_32619138.R
import com.fit2081.nutritrack_timming_32619138.WELCOME_DISCLAIMER
import com.fit2081.nutritrack_timming_32619138.WELCOME_MONASH_LINK
import com.fit2081.nutritrack_timming_32619138.nav.NavigationEventHandler
import com.fit2081.nutritrack_timming_32619138.nav.Navigator
import com.fit2081.nutritrack_timming_32619138.ui.theme.Accent
import com.fit2081.nutritrack_timming_32619138.ui.theme.NutriTrackTheme
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary600

@Composable
fun WelcomeScreen(innerPadding: PaddingValues, navigator: Navigator, viewModel: WelcomeViewModel) {
    val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()

    NavigationEventHandler(
        navigationEvent = navigationEvent,
        navigator = navigator,
        onNavigationHandled = viewModel::onNavigationHandled
    )

    WelcomeContent(innerPadding, onClick = { viewModel.onNavigateToLogin() })
}

@Composable
fun WelcomeContent(innerPadding: PaddingValues, onClick: () -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(24.dp),
    ) {
        Column(
            modifier = Modifier.padding(bottom = 80.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(150.dp)
                    .clip(shape = RoundedCornerShape(999.dp))
            )

            Text(
                "NutriTrack",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Personalised nutritional choices",
                color = Primary600,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = WELCOME_DISCLAIMER,
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Visit Monash Nutrition Clinic",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(WELCOME_MONASH_LINK))
                        context.startActivity(intent)
                    }
            )


            Spacer(modifier = Modifier.height(32.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent
                ),
                onClick = {
                    onClick()
                }
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))


            Text(
                "Made with \uD83D\uDC96 by Kok Tim Ming (32619138)",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeScreen() {
    NutriTrackTheme {
        WelcomeContent(innerPadding = PaddingValues(0.dp), onClick = {})
    }
}