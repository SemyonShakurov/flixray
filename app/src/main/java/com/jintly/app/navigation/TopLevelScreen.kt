package com.jintly.app.navigation

import androidx.annotation.StringRes
import com.jintly.app.R
import ru.jintly.core.designsystem.icon.DrawableResourceIcon
import ru.jintly.core.designsystem.icon.Icons
import ru.jintly.feature.privatesessions.navigation.PRIVATE_SESSIONS_ROUTE
import ru.jintly.feature.profile.navigation.PROFILE_ROUTE
import ru.jintly.publicsessions.navigation.PUBLIC_SESSION_ROUTE

sealed class TopLevelScreen(
    val route: String,
    val icon: DrawableResourceIcon,
    @StringRes val titleId: Int,
) {

    object PublicSessions : TopLevelScreen(
        route = PUBLIC_SESSION_ROUTE,
        icon = DrawableResourceIcon(Icons.PublicSessions),
        titleId = R.string.bottom_bar_public_sessions,
    )

    object PrivateSessions : TopLevelScreen(
        route = PRIVATE_SESSIONS_ROUTE,
        icon = DrawableResourceIcon(Icons.PrivateSessions),
        titleId = R.string.bottom_bar_private_sessions,
    )

    object Profile : TopLevelScreen(
        route = PROFILE_ROUTE,
        icon = DrawableResourceIcon(Icons.Profile),
        titleId = R.string.bottom_bar_profile,
    )
}
