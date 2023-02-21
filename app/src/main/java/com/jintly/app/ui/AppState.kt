package com.jintly.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ru.jintly.core.data.utils.NetworkMonitor

@Composable
fun rememberAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): AppState = remember(coroutineScope, networkMonitor) {
    AppState(coroutineScope, networkMonitor)
}

@Stable
class AppState(
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
) {

    val isOnline = networkMonitor.isOnline
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )
}
