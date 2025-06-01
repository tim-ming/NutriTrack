package com.fit2081.nutritrack_timming_32619138.screens.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
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
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary600

@Composable
fun RegisterNameScreen(
    innerPadding: PaddingValues,
    navigator: Navigator,
    viewModel: RegisterViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()

    NavigationEventHandler(
        navigationEvent = navigationEvent,
        navigator = navigator,
        onNavigationHandled = viewModel::onNavigationHandled
    )

    RegisterNameContent(
        innerPadding = innerPadding,
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onBackClick = viewModel::onNameScreenBackClick,
        onContinueClick = viewModel::onNameScreenContinueClick,
    )
}

@Composable
fun RegisterNameContent(
    innerPadding: PaddingValues,
    uiState: RegisterUiState,
    onNameChange: (TextFieldValue) -> Unit,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
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
            TopBackButton(onBackClick, "Verify account")
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    "Enter your name",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Tell us how we should address you. You can always change your name later in settings.",
                    style = MaterialTheme.typography.titleMedium,
                    color = Primary500
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            ErrorBox(uiState.errors.universal)

            Spacer(modifier = Modifier.height(8.dp))

            RegisterNameField(uiState.name, onNameChange, uiState.errors.nameField, onDone = onContinueClick)

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
                enabled = uiState.name.text.isNotEmpty(),
                onClick = {
                    onContinueClick()
                }
            ) {
                Text(
                    text = "Continue",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            ErrorBox(uiState.errors.universal)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                "This app is only for pre-registered users. Please have your ID and phone number handy before continuing.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(300.dp),
                color = Primary600
            )
        }
    }
}

@Composable
fun RegisterNameField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    errorMessage: String,
    onDone: () -> Unit
) {
    val isError = errorMessage.isNotEmpty()
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Name",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("e.g. Tim Ming") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (value.text.isNotEmpty()) {
                        onDone()
                    }
                }
            ),
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRegisterNameScreen() {
    NutriTrackTheme {
        RegisterNameContent(
            innerPadding = PaddingValues(0.dp),
            uiState = RegisterUiState(
                password = TextFieldValue("password123"),
                confirmPassword = TextFieldValue("password123")
            ),
            onBackClick = {},
            onNameChange = {},
            onContinueClick = {},
        )
    }
}