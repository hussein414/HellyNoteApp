package com.example.hellynoteapp.data.usecase

import com.example.hellynoteapp.data.model.Note
import com.example.hellynoteapp.data.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}