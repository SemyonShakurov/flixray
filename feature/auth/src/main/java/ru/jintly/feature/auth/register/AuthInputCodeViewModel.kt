package ru.jintly.feature.auth.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.jintly.feature.auth.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthInputCodeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _code: Int? = savedStateHandle["code"]
    private val email: String? = savedStateHandle["email"]

    fun onConfirmCodeClick(
        code: Int,
        onConfirmCodeSuccess: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                authRepository.confirmCode(email ?: "", _code ?: 0)
                onConfirmCodeSuccess(email ?: "")
            } catch (e: Throwable) {
                val a = e
            }
        }
    }
}
