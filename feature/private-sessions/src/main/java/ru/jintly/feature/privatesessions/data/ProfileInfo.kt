package ru.jintly.feature.privatesessions.data

import android.graphics.Bitmap

data class ProfileInfo(
    val name: String,
    val email: String,
    val avatar: Bitmap? = null,
    val isAdded: Boolean = false,
)
