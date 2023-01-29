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
    
    @State private var isNoteSelected = false //TODO: remove
    @State private var selectedNoteId: Int64? = nil //TODO: remove
    
    init(noteDataSource: NoteDateSource) {
        self.noteDataSource = noteDataSource
    }
    
    var body: some View {
        VStack {
            ZStack {
                //TODO: remove
                NavigationLink(destination: NoteDetailScreen(noteDataSource: self.noteDataSource, noteId: selectedNoteId), isActive: $isNoteSelected) {
                    EmptyView()
                }.hidden()
                
                HideableSearchTextField(
                    onSearhToggled: {
                        viewModel.toggleIsSearchActive()
                    },
                    destinationProvider: {
                        NoteDetailScreen(noteDataSource: self.noteDataSource, noteId: selectedNoteId)
                    },
                    isSearchActive: viewModel.isSearchActive,
                    searchText: $viewModel.searchText
                ).frame(maxWidth: .infinity, minHeight: 40)
                    .padding()
                
                if !viewModel.isSearchActive {
                    Text("All notes")
                        .font(.title2)
                }
            } 
            
            List {
                ForEach(viewModel.filteredNotes, id: \.self.id) { note in
                    Button(action: { //TODO: I think I should change button to Navigation link and remove all this state bullshit
                        isNoteSelected = true
                        selectedNoteId = note.id?.int64Value
                    }) {
                        NoteItem(
                            note: note,
                            onDeleteClick: {
                                viewModel.deleteNoteById(id: note.id?.int64Value)
                            }
                        )
                    }
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
        //EmptyView()
        let databaseModule = DatabaseModule()
        
        NoteListScreen(noteDataSource: databaseModule.noteDataSource) //TODO: how to preview this thing?
    }
}
