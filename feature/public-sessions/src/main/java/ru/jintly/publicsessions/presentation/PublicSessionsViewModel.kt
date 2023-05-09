package ru.jintly.publicsessions.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.jintly.publicsessions.model.PublicSessionData
import javax.inject.Inject

@HiltViewModel
class PublicSessionsViewModel @Inject constructor() : ViewModel() {

    val uiState: StateFlow<PublicSessionsUiState> =
        MutableStateFlow(
            PublicSessionsUiState.Success(
                sessions = listOf(
                    PublicSessionData(
                        title = "Akamai",
                        image = null,
                        uri = "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8",
                    ),
                    PublicSessionData(
                        title = "Parkour",
                        image = null,
                        uri = "http://81.200.150.77:8082/minecraft/output.m3u8",
                    ),
                    PublicSessionData(
                        title = "Minecraft",
                        image = null,
                        uri = "http://81.200.150.77:8082/minecraft/output.m3u8",
                    ),
                ),
            ),
        )
}

sealed interface PublicSessionsUiState {

    object Loading : PublicSessionsUiState

    data class Success(
        val sessions: List<PublicSessionData>,
    ) : PublicSessionsUiState
}
