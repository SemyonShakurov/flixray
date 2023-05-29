package ru.jintly.feature.auth.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthCreatePasswordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val email: String? = savedStateHandle["email"]

    fun onCompleteAuthClick(password: String, onAuthSuccess: (String, String) -> Unit) {
        if (password.isNotBlank() && !email.isNullOrBlank()) {
            onAuthSuccess(password, email)
        }
    }
}
