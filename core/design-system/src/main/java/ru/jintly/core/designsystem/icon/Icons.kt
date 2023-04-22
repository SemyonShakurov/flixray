package ru.jintly.core.designsystem.icon

import androidx.annotation.DrawableRes
import ru.jintly.core.designsystem.R

object Icons {
    val PublicSessions = R.drawable.ic_public_sessions
    val PrivateSessions = R.drawable.ic_private_sessions
    val Profile = R.drawable.ic_profile
}

data class DrawableResourceIcon(@DrawableRes val id: Int)
