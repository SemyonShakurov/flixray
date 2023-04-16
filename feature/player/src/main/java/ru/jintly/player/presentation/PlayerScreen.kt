package ru.jintly.player.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.jintly.player.presentation.view.VideoPlayer

@Composable
internal fun PlayerRoute(
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = hiltViewModel(),
) {
    PlayerScreen(
        modifier = modifier,
    )
}

@Composable
internal fun PlayerScreen(
    modifier: Modifier = Modifier,
) {
    VideoPlayer(
        modifier = modifier,
    )
}
