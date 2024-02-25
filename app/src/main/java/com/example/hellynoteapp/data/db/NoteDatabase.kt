package com.example.hellynoteapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hellynoteapp.data.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
}