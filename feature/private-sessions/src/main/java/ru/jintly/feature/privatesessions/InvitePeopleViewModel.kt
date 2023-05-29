package ru.jintly.feature.privatesessions

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.jintly.core.data.utils.TOKEN_KEY
import ru.jintly.core.data.utils.dataStore
import ru.jintly.feature.privatesessions.data.ProfileInfo
import javax.inject.Inject

@HiltViewModel
class InvitePeopleViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val privateSessionsRepository: PrivateSessionsRepository,
) : ViewModel() {

    private val dataStore = context.dataStore

    private var profiles: MutableList<ProfileInfo> = mutableListOf()
    private val _uiState = MutableStateFlow<InvitePeopleUiState>(InvitePeopleUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.value = InvitePeopleUiState.Success(profiles)
    }

    fun onAddProfileClick(profile: ProfileInfo) {
        profiles.remove(profile)
        profiles.add(profile.copy(isAdded = true))
        profiles.sortBy { !it.isAdded }
        if (_uiState.value is InvitePeopleUiState.Success) {
            _uiState.value = InvitePeopleUiState.NSuccess(profiles)
        } else {
            _uiState.value = InvitePeopleUiState.Success(profiles)
        }
    }

    fun onSearchClick(name: String) {
        if (name.isBlank()) return
        profiles.removeAll { !it.isAdded }
        dataStore.data
            .map { pref ->
                pref[TOKEN_KEY] ?: ""
            }
            .onEach { token ->
                val res = privateSessionsRepository.findByName(token, name)
                if (res != null) {
                    profiles.add(ProfileInfo(res.name, res.email))
                    profiles.sortBy { !it.isAdded }
                    if (_uiState.value is InvitePeopleUiState.Success) {
                        _uiState.value = InvitePeopleUiState.NSuccess(profiles)
                    } else {
                        _uiState.value = InvitePeopleUiState.Success(profiles)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onContinueClick(onSuccess: (String, String) -> Unit) {
//        onSuccess(profiles.filter { it.isAdded }.map { it.email }.toTypedArray())
        onSuccess(
            profiles[0].email,
            profiles[1].email,
        )
    }
}

sealed interface InvitePeopleUiState {

    object Loading : InvitePeopleUiState

    data class Success(
        val profiles: List<ProfileInfo>,
    ) : InvitePeopleUiState

    data class NSuccess(
        val profiles: List<ProfileInfo>,
    ) : InvitePeopleUiState
}
