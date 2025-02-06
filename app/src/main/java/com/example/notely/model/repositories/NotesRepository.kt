package com.example.notely.model.repositories

import androidx.lifecycle.LiveData
import com.example.notely.model.database.NotesDao
import com.example.notely.model.entities.Note

class NotesRepository(private val notesDao: NotesDao) {

    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    suspend fun insert(note: Note) {
        notesDao.insert(note)
    }
}