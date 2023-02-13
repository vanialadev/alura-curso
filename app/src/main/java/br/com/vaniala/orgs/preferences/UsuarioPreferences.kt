package br.com.vaniala.orgs.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 12/02/23.
 *
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sessao_usuario")

val usuarioLogadoPreferences = stringPreferencesKey("usuarioLogado")
