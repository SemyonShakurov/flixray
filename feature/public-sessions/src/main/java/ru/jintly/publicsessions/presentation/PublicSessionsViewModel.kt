package ru.jintly.publicsessions.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.jintly.core.data.utils.NAME_KEY
import ru.jintly.core.data.utils.TOKEN_KEY
import ru.jintly.core.data.utils.dataStore
import ru.jintly.publicsessions.data.PublicSessionsRepository
import ru.jintly.publicsessions.model.PublicSessionData
import javax.inject.Inject

@HiltViewModel
class PublicSessionsViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val publicSessionsRepository: PublicSessionsRepository,
) : ViewModel() {

    private val dataStore = context.dataStore
    private var token: String? = null
    private var profileName: String? = null

    val stateFlow: Flow<State> = flow {
        while (true) {
            if (token != null) {
                val res = publicSessionsRepository.currentInfo(token!!)
                if (res != null) {
                    emit(
                        State(
                            rooms = res.rooms.filter { it.is_private }
                                .map { Room(it.name, it.users_count, it.timing, it.duration, it.admin_name) },
                            count = res.rooms.find { it.name == "Red room" }?.users_count ?: 0,
                        ),
                    )
                }
            }
            delay(5000)
        }
    }

    init {
        dataStore.data
            .onEach { pref ->
                token = pref[TOKEN_KEY]
                profileName = pref[NAME_KEY]
            }
            .launchIn(viewModelScope)
    }

    val uiState: StateFlow<PublicSessionsUiState> =
        MutableStateFlow(
            PublicSessionsUiState.Success(
                sessions = listOf(
                    PublicSessionData(
                        title = "Red room",
                        image = null,
                    ),
                    PublicSessionData(
                        title = "Green room",
                        image = null,
                    ),
                    PublicSessionData(
                        title = "Blue room",
                        image = null,
                    ),
                ),
            ),
        )

    fun onSessionClick(name: String, admin: String, onSuccess: (String, Boolean) -> Unit) {
        if (profileName != null) {
            onSuccess(name, admin == profileName)
        }
    }
}

sealed interface PublicSessionsUiState {

    object Loading : PublicSessionsUiState

    data class Success(
        val sessions: List<PublicSessionData>,
    ) : PublicSessionsUiState
}

data class State(
    val rooms: List<Room>,
    val count: Int,
)

data class Room(
    val name: String,
    val userCount: Int,
    val timing: Int,
    val duration: Int,
    val adminName: String,
)
