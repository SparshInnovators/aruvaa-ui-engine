package com.myproject.testingframework.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.espresso.core.internal.deps.dagger.multibindings.StringKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataStore")

fun createKey(keyName: String): Preferences.Key<String> = stringPreferencesKey(keyName)

suspend fun saveDataToDB(context: Context, key: Preferences.Key<String>, value: String) {
    context.dataStore.edit { preferences ->
        preferences[key] = value
    }
}

fun getDataFromDB(context: Context, key: Preferences.Key<String>): Flow<String> {
    return context.dataStore.data.map { preferences ->
        preferences[key] ?: "No Value"
    }
}