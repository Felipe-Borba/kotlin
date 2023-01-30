//
//  NoteListScreen.swift
//  iosApp
//
//  Created by Felipe Silva de Borba on 29/01/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteListScreen: View {
    private var noteDataSource: NoteDateSource
    @StateObject var viewModel = NoteListViewModel(noteDataSource: nil)
    
    init(noteDataSource: NoteDateSource) {
        self.noteDataSource = noteDataSource
    }
    
    var body: some View {
        VStack {
            ZStack {
                HideableSearchTextField(
                    onSearhToggled: {
                        viewModel.toggleIsSearchActive()
                    },
                    destinationProvider: {
                        NoteDetailScreen(noteDataSource: self.noteDataSource, noteId: nil)
                    },
                    isSearchActive: viewModel.isSearchActive,
                    searchText: $viewModel.searchText
                ).frame(maxWidth: .infinity, minHeight: 40)
                    .padding()
                
                if !viewModel.isSearchActive {
                    Text("All notes").font(.title2)
                }
            } 
            
            List {
                ForEach(viewModel.filteredNotes, id: \.self.id) { note in
                    Button(action: {}) {
                        NoteItem(
                            note: note,
                            onDeleteClick: {
                                viewModel.deleteNoteById(id: note.id?.int64Value)
                            }
                        )
                    }.background(
                        NavigationLink("", destination: {
                            NoteDetailScreen(
                                noteDataSource: self.noteDataSource,
                                noteId: note.id?.int64Value
                            )
                        }).opacity(0)
                    )
                }
            }
            .onAppear {
                viewModel.loadNotes()
            }
            .listStyle(.plain)
            .listRowSeparator(.hidden)
        }.onAppear {
            viewModel.setNoteDataSource(noteDataSource: noteDataSource)
        }
    }
}

struct NoteListScreen_Previews: PreviewProvider {
    
    static var previews: some View {
        let databaseModule = DatabaseModule()
        NoteListScreen(noteDataSource: databaseModule.noteDataSource)
    }
}
