package ru.jintly.feature.auth.register

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthRegisterViewModel @Inject constructor() : ViewModel() {

    fun onSendCodeClick(onSendCodeSuccess: () -> Unit) {
        SystemClock.sleep(1000)
        onSendCodeSuccess()
    }
}
