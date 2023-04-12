package ru.jintly.publicsessions.presentation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun PublicSessionsRoute(
    viewModel: PublicSessionsViewModel = hiltViewModel(),
    onToPlayerClick: () -> Unit,
) {
    PublicSessionsScreen(
        onToPlayerClick = onToPlayerClick,
    )
}

@Composable
internal fun PublicSessionsScreen(
    onToPlayerClick: () -> Unit,
) {
    Button(
        onClick = onToPlayerClick,
    ) {
        Text(text = "To Player")
    }
}
