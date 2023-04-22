package ru.jintly.publicsessions.model

import android.graphics.Bitmap

data class PublicSessionData(
    val title: String,
    val image: Bitmap?,
    val uri: String,
)
