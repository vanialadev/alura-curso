package br.com.vaniala.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.vaniala.orgs.database.AppDatabase
import br.com.vaniala.orgs.extensions.vaiPara
import br.com.vaniala.orgs.model.Usuario
import br.com.vaniala.orgs.preferences.dataStore
import br.com.vaniala.orgs.preferences.usuarioLogadoPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 13/02/23.
 *
 */
abstract class UsuarioBaseActivity : AppCompatActivity() {

    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    private val _usuario: MutableStateFlow<Usuario?> = MutableStateFlow(null)
    protected val usuario: StateFlow<Usuario?> = _usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            verificaUsuarioLogado()
        }
    }

    private suspend fun verificaUsuarioLogado() {
        dataStore.data.collect { preferences ->
            preferences[usuarioLogadoPreferences]?.let { usuarioId ->
                buscaUsuario(usuarioId)
            } ?: vaiParaLogin()
        }
    }

    private suspend fun buscaUsuario(usuarioId: String): Usuario? {
        return usuarioDao
            .buscaPorId(usuarioId)
            .firstOrNull().also {
                _usuario.value = it
            }
    }

    protected suspend fun deslogaUsuario() {
        dataStore.edit { preferences ->
            preferences.remove(usuarioLogadoPreferences)
        }
    }

    private fun vaiParaLogin() {
        vaiPara(LoginActivity::class.java) {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        finish()
    }
}
