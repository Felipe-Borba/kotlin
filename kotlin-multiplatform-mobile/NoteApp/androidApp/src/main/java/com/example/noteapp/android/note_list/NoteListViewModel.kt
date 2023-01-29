package com.example.noteapp.android.note_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.domain.DataTimeUtil
import com.example.noteapp.domain.note.Note
import com.example.noteapp.domain.note.NoteDateSource
import com.example.noteapp.domain.note.SearchNotes
import com.example.noteapp.presentation.RedOrangeHex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDateSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchNotes = SearchNotes()

    private val notes = savedStateHandle.getStateFlow("notes", emptyList<Note>())
    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)

    val state = combine(notes, searchText, isSearchActive) { notes, searchText, isSearchActive ->
        NoteListState(
            notes = searchNotes.execute(notes, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteListState())

//    init {
//        viewModelScope.launch {
//            (1 .. 5).forEach{
//                noteDataSource.insertNote(
//                    Note(
//                        id = null,
//                        title = "Title $it",
//                        content = "Content $it",
//                        created = DataTimeUtil.now(),
//                        colorHex = RedOrangeHex,
//                    )
//                )
//            }
//        }
//    }

    fun loadNotes() {
        viewModelScope.launch {
            savedStateHandle["notes"] = noteDataSource.getAllNotes()
        }
    }

    fun onSearchTextChange(text: String) {
        savedStateHandle["searchText"] = text
    }

    fun onToggleSearch() {
        savedStateHandle["isSearchActive"] = !isSearchActive.value
        if (!isSearchActive.value) {
            savedStateHandle["searchText"] = ""
        }
    }

    fun deleteNoteById(id: Long) {
        viewModelScope.launch {
            noteDataSource.deleteNote(id)
            loadNotes()
        }
    }

}