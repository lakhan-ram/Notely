package com.example.notely.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notely.model.database.NotesDatabase
import com.example.notely.model.entities.Note
import com.example.notely.model.repositories.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application): AndroidViewModel(application) {
    val allNotes: LiveData<List<Note>>
    private val notesRepository: NotesRepository

    init {
        val notesDao = NotesDatabase.getDatabase(application).notesDao()
        notesRepository = NotesRepository(notesDao)
        allNotes = notesRepository.allNotes
    }

    fun addNote(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            notesRepository.insert(note)
        }
    }

    fun updateNote(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            notesRepository.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            notesRepository.deleteNote(note)
        }
    }
}