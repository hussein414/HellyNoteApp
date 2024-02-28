package com.example.hellynoteapp.ui.state

import java.time.LocalDate
import java.time.temporal.TemporalAccessor

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true,
)
