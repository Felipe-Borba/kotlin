package com.example.noteapp.domain.note

import com.example.noteapp.presentation.*
import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime
) {
    companion object {
        private val colors = listOf(RedOrangeHex, RedPinkHex, VioletHex, LightGreenHex, BabyBlueHex)

        fun generateRandomColor() = colors.random()
    }
}
