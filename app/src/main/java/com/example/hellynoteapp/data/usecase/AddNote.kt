package com.example.hellynoteapp.data.usecase

import com.example.hellynoteapp.data.model.Note
import com.example.hellynoteapp.data.repository.NoteRepository
import com.example.hellynoteapp.utils.InvalidNoteException

class AddNote(private val repository: NoteRepository) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty.")
        }
        repository.insertNote(note)
    }
}