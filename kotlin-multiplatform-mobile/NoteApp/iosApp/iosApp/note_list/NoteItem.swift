//
//  NoteItem.swift
//  iosApp
//
//  Created by Felipe Silva de Borba on 29/01/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteItem: View {
    var note: Note
    var onDeleteClick: () -> Void
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                Text(note.title)
                    .font(.title3)
                    .fontWeight(.semibold)
                
                Spacer()
                
                Button(action: onDeleteClick) {
                    Image(systemName: "xmark")
                        .foregroundColor(.black)
                }
            }
            .padding(.bottom, 3.0)
            
            Text(note.content)
                .fontWeight(.light)
                .padding(.bottom, 3.0)
            
            HStack {
                Spacer()
                
                Text(DataTimeUtil().formatNoteDate(dateTime: note.created))
                    .font(.footnote)
                    .fontWeight(.light)
            }
        }
        .padding()
        .background(Color(hex: note.colorHex))
        .clipShape(RoundedRectangle(cornerRadius: 5.0))
    }
}

struct NoteItem_Previews: PreviewProvider {
    static var previews: some View {
        NoteItem(
            note: Note(id: nil, title: "Note Title", content: "Some content", colorHex: 0xFA1234, created: DataTimeUtil().now()),
            onDeleteClick: {}
        )
    }
}
