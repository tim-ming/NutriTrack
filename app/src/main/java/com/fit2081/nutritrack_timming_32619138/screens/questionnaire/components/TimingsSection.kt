package com.fit2081.nutritrack_timming_32619138.screens.questionnaire.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.fit2081.nutritrack_timming_32619138.screens.questionnaire.Timing
import com.fit2081.nutritrack_timming_32619138.screens.questionnaire.TimingQuestion
import com.fit2081.nutritrack_timming_32619138.ui.theme.Primary600
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimingsSection(
    timings: List<Timing>,
    onTimingsChange: (Int, LocalDateTime) -> Unit
) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    var showTimePicker by remember { mutableStateOf(false) }
    var currentTimePickerIndex by remember { mutableIntStateOf(0) }

    Text(
        "Timings",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Medium
    )

    Spacer(modifier = Modifier.height(8.dp))

    timings.forEachIndexed { index, timing ->
        TimingRow(
            question = timing.type.question,
            time = timings[index].time.format(timeFormatter),
            onTimeClick = {
                currentTimePickerIndex = index
                showTimePicker = true
            }
        )
    }

    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { showTimePicker = false },
            onConfirm = { hour, minute ->
                val newTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, minute))
                showTimePicker = false
                onTimingsChange(currentTimePickerIndex, newTime)
            },
            initialHour = timings[currentTimePickerIndex].time.hour,
            initialMinute = timings[currentTimePickerIndex].time.minute
        )
    }
}

@Composable
fun TimingRow(
    question: String,
    time: String,
    onTimeClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = question,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(2f)
                .padding(end = 8.dp)
        )

        Button(
            onClick = onTimeClick,
            modifier = Modifier
                .weight(1f)
                .border(1.dp, Primary600, RoundedCornerShape(999.dp)),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Primary600
            ),
        ) {
            Icon(
                imageVector = Icons.Outlined.Schedule,
                contentDescription = "Clock",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(time)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (Int, Int) -> Unit,
    initialHour: Int,
    initialMinute: Int
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true
    )

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 6.dp,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TimePicker(state = timePickerState)

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            onConfirm(timePickerState.hour, timePickerState.minute)
                        }
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TimingSectionPreview() {
    val timings = listOf(
        Timing(TimingQuestion.SLEEP_TIME, LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 15))),
        Timing(TimingQuestion.SLEEP_TIME, LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 15))),
        Timing(TimingQuestion.SLEEP_TIME, LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 15)))
    )
    var timeList = remember {
        timings.map { it.time }.toMutableStateList()
    }
    Column(modifier = Modifier.padding(16.dp)) {
        TimingsSection(
            timings = timings,
            onTimingsChange = { index, newTime ->
                timeList[index] = newTime
            }
        )
    }
}


@Preview
@Composable
fun TimePickerDialogPreview() {
    TimePickerDialog(
        onDismissRequest = { },
        onConfirm = { _, _ -> },
        initialHour = 10,
        initialMinute = 30
    )
}

@Preview
@Composable
fun TimingRowPreview() {
    TimingRow(question = "Sample Question", time = "08:00", onTimeClick = {})
}
