package com.example.hellynoteapp.data.repository

import com.example.hellynoteapp.data.db.NoteDao
import com.example.hellynoteapp.data.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
    override suspend fun insertNote(note: Note) =
        noteDao.insertNote(note)

    override suspend fun deleteNote(note: Note) =
        noteDao.deleteNote(note)

    override fun getNotes(): Flow<List<Note>> =
        noteDao.getNotes()

    override suspend fun getNoteById(id: Int): Note? =
        noteDao.getNoteById(id)
}