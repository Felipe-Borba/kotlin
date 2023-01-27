package com.example.noteapp.domain.note

interface NoteDateSource {
    suspend fun insertNote(note: Note)
    suspend fun getNoteById(id: Long): Note?
    suspend fun getAllNotes(): List<Note>
    suspend fun deleteNote(id: Long)
}