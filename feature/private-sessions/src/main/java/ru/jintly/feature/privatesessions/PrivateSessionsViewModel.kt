package ru.jintly.feature.privatesessions

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.jintly.feature.privatesessions.data.VideoListItem
import javax.inject.Inject

@HiltViewModel
class PrivateSessionsViewModel @Inject constructor() : ViewModel() {

    val uiState: StateFlow<PrivateSessionsUiState> =
        MutableStateFlow(
            PrivateSessionsUiState.Success(
                movies = listOf(
                    Pair(
                        VideoListItem("movie 1"),
                        VideoListItem("movie 2"),
                    ),
                    Pair(
                        VideoListItem("movie 3"),
                        VideoListItem("movie 4"),
                    ),
                ),
            ),
        )
}

sealed interface PrivateSessionsUiState {

    object Loading : PrivateSessionsUiState

    data class Success(
        val movies: List<Pair<VideoListItem, VideoListItem?>>,
    ) : PrivateSessionsUiState
}
