package ru.jintly.feature.auth.register

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthCreatePasswordViewModel @Inject constructor() : ViewModel() {

    fun onCompleteAuthClick(onAuthSuccess: () -> Unit) {
        SystemClock.sleep(1000)
        onAuthSuccess()
    }
}
