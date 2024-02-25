package com.example.hellynoteapp.ui.state

import com.example.hellynoteapp.data.model.Note
import com.example.hellynoteapp.utils.NoteOrder
import com.example.hellynoteapp.utils.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
