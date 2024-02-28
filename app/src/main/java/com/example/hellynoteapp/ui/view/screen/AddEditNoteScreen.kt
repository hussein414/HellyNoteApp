package com.example.hellynoteapp.ui.view.screen

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hellynoteapp.data.service.NoteNotification
import com.example.hellynoteapp.ui.event.AddEditNoteEvent
import com.example.hellynoteapp.ui.event.UiEvent
import com.example.hellynoteapp.ui.view.component.TransparentHintTextField
import com.example.hellynoteapp.ui.viewmodel.AddEditNoteViewModel
import com.example.hellynoteapp.utils.Constance
import com.example.hellynoteapp.utils.Constance.channelID
import com.example.hellynoteapp.utils.Constance.messageExtra
import com.example.hellynoteapp.utils.Constance.notificationID
import com.example.hellynoteapp.utils.Constance.titleExtra
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    var pickedDate = viewModel.datePicker.value
    var pickedTime = viewModel.timePicker.value
    val noteBackgroundAnimaTable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }
    val scope = rememberCoroutineScope()

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    val context = LocalContext.current



    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel(channelID, titleState.text, importance)
    channel.description = contentState.text
    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Continue", TextStyle(color = Color.Black)) {
                timeDialogState.show()
            }
            negativeButton(text = "Cancel", TextStyle(color = MaterialTheme.colorScheme.error))
        },
        backgroundColor = Color.White,
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick and Date",
            allowedDateValidator = { it.dayOfMonth % 2 == 1 },
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = Color.White,
                headerTextColor = Color.Black,
                dateActiveBackgroundColor = Color.Black,
                dateActiveTextColor = Color.White,
                dateInactiveTextColor = Color.Gray,
                calendarHeaderTextColor = Color.Black
            )
        ) {
            pickedDate = it
            viewModel.onEvent(AddEditNoteEvent.ChangeDate(pickedDate))
        }
    }

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(text = "ok", TextStyle(color = Color.Black)) {
                Toast.makeText(context, "The alarm has been confirmed for you", Toast.LENGTH_SHORT)
                    .show()
                scheduleNotification(
                    context,
                    titleState.text,
                    contentState.text,
                    pickedTime,
                    pickedDate
                )
                notificationManager.createNotificationChannel(channel)

            }
            negativeButton(text = "cancel", TextStyle(color = MaterialTheme.colorScheme.error))
        },
        backgroundColor = Color.White,
    ) {
        timepicker(
            initialTime = LocalTime.MAX,
            title = "Pick and Date",
            timeRange = LocalTime.MIDNIGHT..LocalTime.MAX,
            colors = TimePickerDefaults.colors(
                activeBackgroundColor = Color.Black,
                activeTextColor = Color.White,
                inactiveTextColor = Color.Gray,
                selectorColor = Color.Black
            )
        ) {
            pickedTime = it
            viewModel.onEvent(AddEditNoteEvent.ChangeTime(pickedTime))

        }
    }
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(AddEditNoteEvent.SaveNote)
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
                }
                Spacer(modifier = Modifier.height(10.dp))
                FloatingActionButton(
                    onClick = {
                        dateDialogState.show()
                    },
                    containerColor = MaterialTheme.colorScheme.error
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsActive,
                        contentDescription = "Save note"
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimaTable.value)
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Constance.noteColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimaTable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxHeight()
            )

        }
    }


}

@SuppressLint("ScheduleExactAlarm")
private fun scheduleNotification(
    context: Context,
    title: String,
    message: String,
    localTime: LocalTime,
    localDate: LocalDate
) {
    val intent = Intent(context, NoteNotification::class.java)
    intent.putExtra(titleExtra, title)
    intent.putExtra(messageExtra, message)

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        notificationID,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val dateEpoch = localDate.toEpochDay()
    val timeNano = localTime.toNanoOfDay()
    val combinedTime = dateEpoch + (timeNano / 1_000_000) // تبدیل نانوثانیه به میلی‌ثانیه
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        combinedTime,
        pendingIntent
    )
}