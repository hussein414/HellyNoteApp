package com.example.hellynoteapp.ui.event

sealed class UiEvent {
    data class ShowSnackBar(val message: String): UiEvent()
    object SaveNote: UiEvent()
}