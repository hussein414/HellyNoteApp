package com.example.hellynoteapp.data.repository

import com.example.hellynoteapp.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

}