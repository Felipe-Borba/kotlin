package com.example.noteapp.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.domain.DataTimeUtil
import com.example.noteapp.domain.note.Note
import com.example.noteapp.domain.note.NoteDateSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDateSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow("noteTitle", "")
    private val isNoteTitleHintVisible =
        savedStateHandle.getStateFlow("isNoteTitleHintVisible", false)
    private val noteContent = savedStateHandle.getStateFlow("noteContent", "")
    private val isNoteContentHintVisible =
        savedStateHandle.getStateFlow("isNoteContentHintVisible", false)
    private val noteColor = savedStateHandle.getStateFlow("noteColor", Note.generateRandomColor())

    val state = combine(
        noteTitle,
        isNoteTitleHintVisible,
        noteContent,
        isNoteContentHintVisible,
        noteColor
    ) { noteTitle, isNoteTitleFocused, noteContent, isNoteContentFocused, noteColor ->
        NoteDetailState(
            noteTitle = noteTitle,
            isNoteContentHintVisible = noteContent.isEmpty() && !isNoteContentFocused,
            noteContent = noteContent,
            isNoteTitleHintVisible = noteTitle.isEmpty() && !isNoteTitleFocused,
            noteColor = noteColor
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let { existingNoteId ->
            if (existingNoteId == -1L) {
                return@let
            }

            this.existingNoteId = existingNoteId
            viewModelScope.launch {
                noteDataSource.getNoteById(existingNoteId)?.let { note ->
                    savedStateHandle["noteTitle"] = note.title
                    savedStateHandle["noteContent"] = note.content
                    savedStateHandle["noteColor"] = note.colorHex
                }
            }
        }
    }

    fun onNoteTitleChange(text: String) {
        savedStateHandle["noteTitle"] = text
    }

    fun onNoteContentChange(text: String) {
        savedStateHandle["noteContent"] = text
    }

    fun onNoteTitleHintVisibleChange(isFocused: Boolean) {
        savedStateHandle["isNoteTitleHintVisible"] = isFocused
    }

    fun onNoteContentHintVisibleChange(isFocused: Boolean) {
        savedStateHandle["isNoteContentHintVisible"] = isFocused
    }

    fun saveNote() {
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    created = DataTimeUtil.now()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }
}