package com.fit2081.nutritrack_timming_32619138.screens.settings


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fit2081.nutritrack_timming_32619138.commonUI.toast.ToastContent
import com.fit2081.nutritrack_timming_32619138.commonUI.toast.ToastMessage
import com.fit2081.nutritrack_timming_32619138.data.patient.Patient
import com.fit2081.nutritrack_timming_32619138.nav.NavigationEventHandler
import com.fit2081.nutritrack_timming_32619138.nav.Navigator
import com.fit2081.nutritrack_timming_32619138.state.StateHandler
import com.fit2081.nutritrack_timming_32619138.ui.theme.Accent
import com.fit2081.nutritrack_timming_32619138.ui.theme.NutriTrackTheme
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary200
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary600

@Composable
fun SettingsScreen(
    innerPadding: PaddingValues,
    navigator: Navigator,
    viewModel: SettingsViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val loadingState by viewModel.loadingState.collectAsStateWithLifecycle()

    val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()

    NavigationEventHandler(
        navigationEvent = navigationEvent,
        navigator = navigator,
        onNavigationHandled = viewModel::onNavigationHandled
    )

    StateHandler(
        state = loadingState,
        navigator = navigator,
        onSuccess = {
            SettingsContent(
                innerPadding = innerPadding,
                onNameChange = viewModel::onNameChange,
                state = uiState,
                onToggleEditName = viewModel::onToggleEditName,
                onSaveNameChange = viewModel::onSaveNameChange,

                onClinicianLoginRowClick = viewModel::onClinicianLoginRowClick,
                onLogoutClick = viewModel::onLogoutClick,
            )
        }
    )
}


@Composable
fun SettingsContent(
    innerPadding: PaddingValues,
    state: SettingsUiState,
    onNameChange: (TextFieldValue) -> Unit,
    onToggleEditName: () -> Unit,
    onSaveNameChange: () -> Unit,
    onClinicianLoginRowClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    val name = state.patient.name

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "Settings",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            "Personal Information",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        )
        {
            HorizontalDivider(color = Primary200)
            SettingsEditableDetailsRow(
                title = "Full Name",
                value = name,
                isEditing = state.isNameDropdownVisible,
                toggleEditing = onToggleEditName,
                fieldValue = state.nameField,
                onValueChange = onNameChange,
                onSave = onSaveNameChange,
                openedDescription = "This is how we will address you in the app.",
                errorMessage = state.errorMessage
            )
            HorizontalDivider(color = Primary200)
            SettingsNotEditableDetailsRow("Phone Number", state.patient.phoneNumber)
            HorizontalDivider(color = Primary200)
            SettingsNotEditableDetailsRow("User ID", state.patient.userId)
            HorizontalDivider(color = Primary200)
        }

        Spacer(modifier = Modifier.height(48.dp))
        Text(
            "Others",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        )
        {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(1.dp, Primary200, shape = RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                tonalElevation = 2.dp,
                color = Color.Transparent,
                onClick = onClinicianLoginRowClick
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person4,
                        contentDescription = "Clinician icon",
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Clinician login",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Access advanced insights on patients",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Primary600
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Go to login",
                        tint = Primary600
                    )
                }
            }
            HorizontalDivider(color = Primary200)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    color = Color.Transparent,
                    onClick = onClinicianLoginRowClick
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Go to login",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            modifier = Modifier.clickable { onLogoutClick() },
                            text = "Log out",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SettingsEditableDetailsRow(
    title: String,
    value: String,
    isEditing: Boolean,
    toggleEditing: () -> Unit,
    fieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onSave: () -> Unit,
    openedDescription: String,
    errorMessage: String,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val isError = errorMessage.isNotEmpty()
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = if (isEditing) openedDescription else (value.ifEmpty { "Not set" }),
                    color = Primary600,
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                modifier = Modifier.clickable { toggleEditing() },
                text = if (isEditing) "Cancel" else "Edit",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline
            )
        }


        AnimatedVisibility(visible = isEditing) {
            Column {
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = fieldValue,
                    onValueChange = onValueChange,
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (fieldValue.text.isNotEmpty()) {
                                onSave()
                                keyboardController?.hide()
                            }
                        }
                    ),
                    isError = isError,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
                    ),
                )
                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage, color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    modifier = Modifier
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Accent
                    ),
                    onClick = onSave
                ) {
                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsNotEditableDetailsRow(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                style = MaterialTheme.typography.bodyLarge,
                text = value,
                color = Primary600,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    NutriTrackTheme {
        SettingsContent(
            innerPadding = PaddingValues(0.dp),
            state = SettingsUiState(
                Patient.MOCK,
                false,
                TextFieldValue("Hello")
            ),
            onNameChange = {},
            onToggleEditName = {},
            onClinicianLoginRowClick = {},
            onSaveNameChange = {},
            onLogoutClick = {}
        )
    }
}