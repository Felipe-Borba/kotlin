//
//  NoteListViewModel.swift
//  iosApp
//
//  Created by Felipe Silva de Borba on 29/01/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteListScreen {
    @MainActor class NoteListViewModel: ObservableObject {
        private var noteDataSource: NoteDateSource? = nil
        
        private let searchNotes = SearchNotes()
        private var notes = [Note]()
        @Published private(set) var filteredNotes = [Note]()
        
        @Published var searchText = "" {
            didSet {
                self.filteredNotes = searchNotes.execute(notes: self.notes, query: searchText)
            }
        }
        
        @Published private(set) var isSearchActive = false
        
        init(noteDataSource: NoteDateSource? = nil) {
            self.noteDataSource = noteDataSource
        }
        
        func loadNotes() {
            noteDataSource?.getAllNotes(completionHandler: { notes, error in
                self.notes = notes ?? []
                self.filteredNotes = self.notes
            })
        }
        
        func deleteNoteById(id: Int64?) {
            if id != nil {
                noteDataSource?.deleteNote(id: id!, completionHandler: { error in
                    self.loadNotes()
                })
            }
        }
        
        func toggleIsSearchActive() {
            isSearchActive = !isSearchActive
            if !isSearchActive {
                searchText = ""
            }
        }
        
        func setNoteDataSource(noteDataSource: NoteDateSource) {
            self.noteDataSource = noteDataSource
        }
    }
}
