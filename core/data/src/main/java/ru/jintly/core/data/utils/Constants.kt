package ru.jintly.core.data.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data")

val TOKEN_KEY = stringPreferencesKey("token")
val NAME_KEY = stringPreferencesKey("name")
