package com.example.hellynoteapp.ui.event

import com.example.hellynoteapp.data.model.Note
import com.example.hellynoteapp.utils.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}