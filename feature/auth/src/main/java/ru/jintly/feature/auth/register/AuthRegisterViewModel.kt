package ru.jintly.feature.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.jintly.feature.auth.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthRegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    fun onSendCodeClick(email: String, onSendCodeSuccess: (Int, String) -> Unit) {
        if (email.isNotBlank()) {
            viewModelScope.launch {
                val response = authRepository.regEmail(email)
                val code = response?.code
                if (code != null) {
                    onSendCodeSuccess(code, email)
                }
            }
        }
    }
}
