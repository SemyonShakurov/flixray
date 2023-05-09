package ru.jintly.feature.privatesessions

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.jintly.feature.privatesessions.data.ProfileInfo
import javax.inject.Inject

@HiltViewModel
class InvitePeopleViewModel @Inject constructor() : ViewModel() {

    private var profiles: MutableList<ProfileInfo>? = null

    private val _uiState = MutableStateFlow<InvitePeopleUiState>(InvitePeopleUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        profiles = mutableListOf(
            ProfileInfo("user 1"),
            ProfileInfo("user 2"),
            ProfileInfo("user 3"),
        )
        _uiState.value = InvitePeopleUiState.Success(profiles!!)
    }

    fun onAddProfileClick(profile: ProfileInfo) {
        profiles?.remove(profile)
        profiles?.add(profile.copy(isAdded = true))
        profiles?.sortBy { !it.isAdded }
        if (_uiState.value is InvitePeopleUiState.Success) {
            _uiState.value = InvitePeopleUiState.NSuccess(profiles!!)
        } else {
            _uiState.value = InvitePeopleUiState.Success(profiles!!)
        }
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
