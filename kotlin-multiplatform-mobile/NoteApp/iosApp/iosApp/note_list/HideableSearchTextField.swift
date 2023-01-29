//
//  HideableSearchTextField.swift
//  iosApp
//
//  Created by Felipe Silva de Borba on 29/01/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct HideableSearchTextField<Destination: View>: View {
    
    var onSearhToggled: () -> Void
    var destinationProvider: () -> Destination
    var isSearchActive: Bool
    @Binding var searchText: String
    
    var body: some View {
        HStack {
            TextField("Search...", text: $searchText)
                .textFieldStyle(.roundedBorder)
                .opacity(isSearchActive ? 1 : 0)
            Button(action: onSearhToggled) {
                Image(systemName: isSearchActive ? "xmark": "magnifyingglass")
                    .foregroundColor(/*@START_MENU_TOKEN@*/.black/*@END_MENU_TOKEN@*/)
            }
            NavigationLink(destination: destinationProvider()) {
                Image(systemName: "plus")
                    .foregroundColor(Color.black)
            }
        }
    }
}

struct HideableSearchTextField_Previews: PreviewProvider {
    static var previews: some View {
        HideableSearchTextField(
            onSearhToggled: {},
            destinationProvider: { EmptyView()},
            isSearchActive: true,
            searchText: .constant("Hello World")
        )
    }
}
