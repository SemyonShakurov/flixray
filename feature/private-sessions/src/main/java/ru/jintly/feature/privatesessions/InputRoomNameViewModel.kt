package ru.jintly.feature.privatesessions

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.jintly.core.data.utils.TOKEN_KEY
import ru.jintly.core.data.utils.dataStore
import javax.inject.Inject

@HiltViewModel
class InputRoomNameViewModel @Inject constructor(
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle,
    private val privateSessionsRepository: PrivateSessionsRepository,
) : ViewModel() {

    private val dataStore = context.dataStore

    private val friend1: String = savedStateHandle["friend1"] ?: ""
    private val friend2: String = savedStateHandle["friend2"] ?: ""

    fun onContinueClick(name: String, onSuccess: () -> Unit) {
        dataStore.data
            .map { pref ->
                pref[TOKEN_KEY] ?: ""
            }
            .onEach { token ->
                privateSessionsRepository.createRoom(token, name, listOf(friend1, friend2))
                onSuccess()
            }
            .launchIn(viewModelScope)
    }
}
