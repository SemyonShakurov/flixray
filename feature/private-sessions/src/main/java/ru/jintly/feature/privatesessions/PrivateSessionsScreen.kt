package ru.jintly.feature.privatesessions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.jintly.core.designsystem.colors.Secondary
import ru.jintly.feature.privatesessions.data.VideoListItem

@Composable
internal fun PrivateSessionsRoute(
    viewModel: PrivateSessionsViewModel = hiltViewModel(),
    onMovieClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PrivateSessionsScreen(
        uiState = uiState,
        onMovieClick = onMovieClick,
    )
}

@Composable
internal fun PrivateSessionsScreen(
    uiState: PrivateSessionsUiState,
    modifier: Modifier = Modifier,
    onMovieClick: () -> Unit,
) {
    if (uiState is PrivateSessionsUiState.Success) {
        val movies = uiState.movies

        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    Text(
                        text = "Выберите фильм или сериал",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                items(movies) { movie ->
                    VideoListRaw(moviesPair = movie, onMovieClick = onMovieClick)
                }
            }
        }
    }
}

@Composable
private fun VideoListRaw(
    moviesPair: Pair<VideoListItem, VideoListItem?>,
    onMovieClick: () -> Unit,
) {
    val first = moviesPair.first
    val second = moviesPair.second

    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f).clickable { onMovieClick() },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(width = 156.dp, 92.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Secondary),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = first.title,
                color = Color.White,
                fontSize = 16.sp,
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f).clickable { onMovieClick() },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(width = 156.dp, 92.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Secondary),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = second?.title ?: "movie ?",
                color = Color.White,
                fontSize = 16.sp,
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}
