//
//  NoteDetailViewModel.swift
//  iosApp
//
//  Created by Felipe Silva de Borba on 29/01/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteDetailScreen {
    @MainActor class NoteDetailViewModel: ObservableObject {
        private var noteDataSource: NoteDateSource?
        
        private var noteId: Int64? = nil
        @Published var noteTitle = ""
        @Published var noteContent = ""
        @Published private(set) var noteColor = Note.Companion().generateRandomColor()
        
        init(noteDataSource: NoteDateSource? = nil) {
            self.noteDataSource = noteDataSource
        }
        
        func loadNoteIfExists(id: Int64?) {
            if id != nil {
                self.noteId = id
                noteDataSource?.getNoteById(id: id!, completionHandler: { note, error in
                    self.noteTitle = note?.title ?? ""
                    self.noteContent = note?.content ?? ""
                    self.noteColor = note?.colorHex ?? Note.Companion().generateRandomColor()
                })
            }
        }
        
        func saveNote(onSaved: @escaping () -> Void) {
            noteDataSource?.insertNote(
                note: Note(id: noteId == nil ? nil : KotlinLong(value: noteId!), title: noteTitle, content: noteContent, colorHex: noteColor, created: DataTimeUtil().now()),
                completionHandler: { Error in onSaved() }
            )
        }
        
        func setParamsAndLoadNote(noteDataSource: NoteDateSource, noteId: Int64?) {
            self.noteDataSource = noteDataSource
            loadNoteIfExists(id: noteId)
        }
    }
}
