package com.fit2081.nutritrack_timming_32619138.screens.forgotPassword

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fit2081.nutritrack_timming_32619138.ErrorMessages
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
fun ForgotPasswordResetScreen(
    innerPadding: PaddingValues,
    navigator: Navigator,
    viewModel: ForgotPasswordViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()

    NavigationEventHandler(
        navigationEvent = navigationEvent,
        navigator = navigator,
        onNavigationHandled = viewModel::onNavigationHandled
    )

    ForgotPasswordResetContent(
        innerPadding = innerPadding,
        uiState = uiState,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
        onToggleConfirmPasswordVisibility = viewModel::onToggleConfirmPasswordVisibility,
        onConfirmClick = viewModel::onConfirmClick,
        onBackClick = viewModel::onPasswordScreenBackClick,
    )
}

@Composable
fun ForgotPasswordResetContent(
    innerPadding: PaddingValues,
    uiState: ForgotPasswordUiState,
    onPasswordChange: (TextFieldValue) -> Unit,
    onConfirmPasswordChange: (TextFieldValue) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onToggleConfirmPasswordVisibility: () -> Unit,
    onConfirmClick: () -> Unit,
    onBackClick: () -> Unit,
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
            TopBackButton(onBackClick, "Verify")
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    "Change your password",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Create a new secure password to protect your account.",
                    style = MaterialTheme.typography.titleMedium,
                    color = Primary500
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            ErrorBox(uiState.errors.universal)

            Spacer(modifier = Modifier.height(8.dp))

            PasswordField(
                value = uiState.password,
                onValueChange = onPasswordChange,
                errorMessage = uiState.errors.passwordField,
                passwordVisible = uiState.passwordVisible,
                onDone = onConfirmClick,
                onTogglePasswordVisibility = onTogglePasswordVisibility,
            )

            Spacer(modifier = Modifier.height(24.dp))

            ConfirmPasswordField(
                value = uiState.confirmPassword,
                onValueChange = onConfirmPasswordChange,
                errorMessage = uiState.errors.confirmPasswordField,
                passwordVisible = uiState.confirmPasswordVisible,
                onTogglePasswordVisibility = onToggleConfirmPasswordVisibility,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent,
                    disabledContainerColor = Primary200,
                    disabledContentColor = Primary400
                ),
                enabled = uiState.password.text.isNotEmpty() && uiState.confirmPassword.text.isNotEmpty(),
                onClick = {
                    onConfirmClick()
                }
            ) {
                Text(
                    text = "Change password",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PasswordField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    errorMessage: String,
    passwordVisible: Boolean,
    onDone: () -> Unit,
    onTogglePasswordVisibility: () -> Unit,
) {
    val isError = errorMessage.isNotEmpty()
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Password",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                "(at least 8 characters long)",
                style = MaterialTheme.typography.bodyLarge,
                color = Primary600
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("Create a password") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (value.text.isNotEmpty()) {
                        onDone()
                    }
                }
            ),
            trailingIcon = {
                val image =
                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description =
                    if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { onTogglePasswordVisibility() }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
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

@Composable
fun ConfirmPasswordField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    errorMessage: String,
    passwordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
) {
    val isError = errorMessage.isNotEmpty()
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Confirm Password",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("Confirm your password") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image =
                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description =
                    if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { onTogglePasswordVisibility() }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
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
fun PreviewForgotPasswordResetScreen() {
    NutriTrackTheme {
        ForgotPasswordResetContent(
            innerPadding = PaddingValues(0.dp),
            uiState = ForgotPasswordUiState(
                password = TextFieldValue("password123"),
                confirmPassword = TextFieldValue("password123"),
                errors = ForgotPasswordError(
                    passwordField = ErrorMessages.PASSWORD_TOO_SHORT,
                    universal = "Error! Something went wrong."
                )
            ),
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onTogglePasswordVisibility = {},
            onToggleConfirmPasswordVisibility = {},
            onConfirmClick = {},
            onBackClick = {},
        )
    }
}