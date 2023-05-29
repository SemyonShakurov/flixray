package ru.jintly.player.presentation.data

data class Message(
    val text: String,
    val profileName: String,
    val isOut: Boolean,
    val isEnter: Boolean = false,
    val isExit: Boolean = false,
)
