package ru.jintly.feature.auth.accountinfo

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.SavedStateHandle
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
class AccountInfoViewModel @Inject constructor(
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val password: String? = savedStateHandle["password"]
    private val email: String? = savedStateHandle["email"]

    private val dataStore = context.dataStore

    fun onAuthCompleteClick(profileName: String, onSuccess: () -> Unit) {
        if (!password.isNullOrBlank() && !email.isNullOrBlank() && profileName.isNotBlank()) {
            viewModelScope.launch {
                try {
                    val res = authRepository.register(email, password, profileName)
                    if (res != null) {
                        val response = authRepository.token(res.email, password)
                        val token = response?.token
                        val name = response?.name
                        if (!token.isNullOrBlank() && !name.isNullOrBlank()) {
                            dataStore.edit { prefs ->
                                prefs[TOKEN_KEY] = token
                                prefs[NAME_KEY] = name
                            }
                            onSuccess()
                        }
                    }
                } catch (_: Throwable) {
                }
            }
        }
    }
}
