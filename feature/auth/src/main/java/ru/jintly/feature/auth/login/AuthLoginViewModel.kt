package ru.jintly.feature.auth.login

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import ru.jintly.core.data.utils.NAME_KEY
import ru.jintly.core.data.utils.TOKEN_KEY
import ru.jintly.core.data.utils.dataStore
import ru.jintly.feature.auth.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthLoginViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val dataStore = context.dataStore

    fun onLoginClick(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val res = authRepository.token(email, password)
                val token = res?.token
                val name = res?.name
                if (!token.isNullOrBlank() && !name.isNullOrBlank()) {
                    dataStore.edit { prefs ->
                        prefs[TOKEN_KEY] = token
                        prefs[NAME_KEY] = name
                    }
                    onSuccess()
                }
            } catch (_: Throwable) {}
        }
    }
}
