package ru.jintly.feature.auth.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthLoginViewModel @Inject constructor() : ViewModel() {

    fun onLoginClick(onSuccess: () -> Unit) {
        onSuccess()
    }
}
