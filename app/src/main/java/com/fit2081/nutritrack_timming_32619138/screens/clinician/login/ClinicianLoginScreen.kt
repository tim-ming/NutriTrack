package com.fit2081.nutritrack_timming_32619138.screens.clinician.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fit2081.nutritrack_timming_32619138.commonUI.ErrorBox
import com.fit2081.nutritrack_timming_32619138.commonUI.TopBackButton
import com.fit2081.nutritrack_timming_32619138.nav.NavigationEventHandler
import com.fit2081.nutritrack_timming_32619138.nav.Navigator
import com.fit2081.nutritrack_timming_32619138.ui.theme.Accent
import com.fit2081.nutritrack_timming_32619138.ui.theme.NutriTrackTheme
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary200
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary400
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary500

@Composable
fun ClinicianLoginScreen(
    innerPadding: PaddingValues,
    navigator: Navigator,
    viewModel: ClinicianLoginViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()

    NavigationEventHandler(
        navigationEvent = navigationEvent,
        navigator = navigator,
        onNavigationHandled = viewModel::onNavigationHandled
    )

    ClinicianLoginContent(
        innerPadding = innerPadding,
        uiState = uiState,
        onPassphraseChange = viewModel::onPassphraseChange,
        onBackClick = viewModel::onBackClick,
        onLoginClick = viewModel::onLoginClick,
        onTogglePassphraseVisibility = viewModel::onTogglePassphraseVisibility
    )
}

@Composable
fun ClinicianLoginContent(
    innerPadding: PaddingValues,
    uiState: ClinicianLoginUiState,
    onPassphraseChange: (TextFieldValue) -> Unit,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onTogglePassphraseVisibility: () -> Unit,
) {

    Box(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp)
            .padding(top = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            TopBackButton(onBackClick, "Settings")
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    "Clinician Login",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Access advanced patients' insights. Enter your clinician passphrase to continue.",
                    style = MaterialTheme.typography.titleMedium,
                    color = Primary500
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ErrorBox(uiState.errorMessage)

            Spacer(modifier = Modifier.height(16.dp))

            PassphraseField(
                value = uiState.passphraseField,
                onValueChange = onPassphraseChange,
                onTogglePassphraseVisibility = onTogglePassphraseVisibility,
                passphraseVisible = uiState.passphraseVisible,
                onDone = onLoginClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent,
                    disabledContainerColor = Primary200,
                    disabledContentColor = Primary400
                ),
                enabled = uiState.passphraseField.text.isNotEmpty(),
                onClick = {
                    onLoginClick()
                }
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PassphraseField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onTogglePassphraseVisibility: () -> Unit,
    passphraseVisible: Boolean,
    onDone: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Passphrase",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("Secret Passphrase") },
            singleLine = true,
            visualTransformation = if (passphraseVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (value.text.isNotEmpty()) {
                        onDone()
                        keyboardController?.hide()
                    }
                }
            ),
            trailingIcon = {
                val image =
                    if (passphraseVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description =
                    if (passphraseVisible) "Hide password" else "Show password"

                IconButton(onClick = { onTogglePassphraseVisibility() }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewClinicianLoginScreen() {
    NutriTrackTheme {
        ClinicianLoginContent(
            innerPadding = PaddingValues(0.dp),
            uiState = ClinicianLoginUiState(
                passphraseField = TextFieldValue("password123"),
                errorMessage = "Error! Something went wrong."
            ),
            onBackClick = {},
            onLoginClick = {},
            onPassphraseChange = {
            },
            onTogglePassphraseVisibility = {}
        )
    }
}