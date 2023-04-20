package ru.jintly.feature.auth.register

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthInputCodeViewModel @Inject constructor() : ViewModel() {

    fun onConfirmCodeClick(onConfirmCodeSuccess: () -> Unit) {
        onConfirmCodeSuccess()
    }
}
