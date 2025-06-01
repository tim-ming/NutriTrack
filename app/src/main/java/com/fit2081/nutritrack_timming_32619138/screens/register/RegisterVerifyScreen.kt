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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.fit2081.nutritrack_timming_32619138.nav.NavigationEventHandler
import com.fit2081.nutritrack_timming_32619138.nav.Navigator
import com.fit2081.nutritrack_timming_32619138.ui.theme.Accent
import com.fit2081.nutritrack_timming_32619138.ui.theme.NutriTrackTheme
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary200
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary400
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary500
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary600

@Composable
fun RegisterVerifyScreen(
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

    RegisterVerifyContent(
        innerPadding = innerPadding,
        uiState = uiState,
        onUidSelected = viewModel::onUidSelected,
        onPhoneNumberChange = viewModel::onPhoneNumberChange,
        onContinueClick = viewModel::onVerifyContinueClick,
        onLoginClick = viewModel::onLoginClick
    )
}

@Composable
fun RegisterVerifyContent(
    innerPadding: PaddingValues,
    uiState: RegisterUiState,
    onUidSelected: (String) -> Unit,
    onPhoneNumberChange: (TextFieldValue) -> Unit,
    onContinueClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp)
            .padding(top = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    "Register",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Verify your account using your pre-registered User ID and phone number.",
                    style = MaterialTheme.typography.titleMedium,
                    color = Primary500
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            ErrorBox(uiState.errors.universal)

            Spacer(modifier = Modifier.height(8.dp))

            RegisterUidDropdownField(
                userIds = uiState.uids,
                selectedUid = uiState.selectedUid,
                onUidSelected = onUidSelected,
                errorMessage = uiState.errors.uidField
            )

            Spacer(modifier = Modifier.height(24.dp))

            RegisterPhoneNumberField(
                value = uiState.phoneNumber,
                onValueChange = onPhoneNumberChange,
                errorMessage = uiState.errors.phoneNumberField,
                onDone = onContinueClick
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
                enabled = uiState.selectedUid.isNotEmpty() && uiState.phoneNumber.text.isNotEmpty(),
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

            HorizontalDivider(color = Primary200)

            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row() {
                    Text(
                        "Already have an account?",
                        style = MaterialTheme.typography.titleMedium,
                        color = Primary600,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "Login",
                        style = MaterialTheme.typography.titleMedium,
                        color = Accent,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable {
                            onLoginClick()
                        }
                    )
                }
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUidDropdownField(
    userIds: List<String>,
    selectedUid: String,
    onUidSelected: (String) -> Unit,
    errorMessage: String
) {
    var expanded by remember { mutableStateOf(false) }

    val borderColor = if (errorMessage.isNotEmpty()) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.outline
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            "User ID",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(4.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedUid,
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text("Select User ID")
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = borderColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                if (userIds.isEmpty()) {
                    DropdownMenuItem(
                        text = { Text("No User IDs found") },
                        onClick = {
                            expanded = false
                        }
                    )
                } else {
                    userIds.sortedBy { it.toInt() }.forEach { uid ->
                        DropdownMenuItem(
                            text = { Text(uid) },
                            onClick = {
                                onUidSelected(uid)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun RegisterPhoneNumberField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    errorMessage: String,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isError = errorMessage.isNotEmpty()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            "Phone Number",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("Enter your phone number") },
            singleLine = true,
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (value.text.isNotEmpty()) {
                        onDone()
                    }
                }
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
fun PreviewRegisterStep1Screen() {
    NutriTrackTheme {
        RegisterVerifyContent(
            innerPadding = PaddingValues(0.dp),
            uiState = RegisterUiState(
                uids = listOf("001", "002"),
                selectedUid = "001",
                phoneNumber = TextFieldValue("1234567890"),
                password = TextFieldValue("password123"),
                confirmPassword = TextFieldValue("password123"),
                errors = RegistrationError(
                    uidField = "Please enter User ID",
                    phoneNumberField = "Please enter phone number",
                    universal = "Error! Something went wrong."
                )
            ),
            onUidSelected = {},
            onPhoneNumberChange = {},
            onContinueClick = {},
            onLoginClick = {}
        )
    }
}