package com.example.hellynoteapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hellynoteapp.data.model.Note

@Database(entities = [Note::class], version = 1)
@TypeConverters(DateTimeTypeConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
}