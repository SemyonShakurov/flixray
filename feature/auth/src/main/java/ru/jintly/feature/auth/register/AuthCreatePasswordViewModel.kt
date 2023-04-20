package ru.jintly.feature.auth.register

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthCreatePasswordViewModel @Inject constructor() : ViewModel() {

    fun onCompleteAuthClick(onAuthSuccess: () -> Unit) {
        onAuthSuccess()
    }
}
