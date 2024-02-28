package com.example.hellynoteapp.ui.event

import androidx.compose.ui.focus.FocusState
import java.time.LocalDate
import java.time.LocalTime

sealed class AddEditNoteEvent {
    data class EnteredTitle(val value: String) : AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditNoteEvent()
    data class EnteredContent(val value: String) : AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState) : AddEditNoteEvent()
    data class ChangeColor(val color: Int) : AddEditNoteEvent()

    data class ChangeDate(val pickedDate: LocalDate) : AddEditNoteEvent()
    data class ChangeTime(val pickedTime: LocalTime) : AddEditNoteEvent()


    object SaveNote : AddEditNoteEvent()
}