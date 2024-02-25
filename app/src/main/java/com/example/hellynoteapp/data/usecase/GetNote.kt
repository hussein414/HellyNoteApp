package com.example.hellynoteapp.data.usecase

import com.example.hellynoteapp.data.model.Note
import com.example.hellynoteapp.data.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}