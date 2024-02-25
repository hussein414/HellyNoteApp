package com.example.hellynoteapp.di

import android.app.Application
import androidx.room.Room
import com.example.hellynoteapp.data.db.NoteDatabase
import com.example.hellynoteapp.data.repository.NoteRepository
import com.example.hellynoteapp.data.repository.NoteRepositoryImpl
import com.example.hellynoteapp.data.usecase.AddNote
import com.example.hellynoteapp.data.usecase.DeleteNote
import com.example.hellynoteapp.data.usecase.GetNote
import com.example.hellynoteapp.data.usecase.GetNotes
import com.example.hellynoteapp.data.usecase.NoteUseCases
import com.example.hellynoteapp.utils.Constance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteAppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase = Room.databaseBuilder(
        app,
        NoteDatabase::class.java,
        Constance.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository = NoteRepositoryImpl(db.noteDao)


    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases = NoteUseCases(
        getNotes = GetNotes(repository),
        deleteNote = DeleteNote(repository),
        addNote = AddNote(repository),
        getNote = GetNote(repository)
    )


}