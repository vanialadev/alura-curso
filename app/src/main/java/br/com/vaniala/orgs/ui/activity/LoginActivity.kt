package br.com.vaniala.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.vaniala.orgs.database.AppDatabase
import br.com.vaniala.orgs.databinding.ActivityLoginBinding
import br.com.vaniala.orgs.extensions.toast
import br.com.vaniala.orgs.extensions.vaiPara
import br.com.vaniala.orgs.preferences.dataStore
import br.com.vaniala.orgs.preferences.usuarioLogadoPreferences
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val dao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()
        configuraBotaoEntrar()
    }
    private fun configuraBotaoEntrar() {
        binding.activityLoginBotaoEntrar.setOnClickListener {
            val usuario = binding.activityLoginUsuario.text.toString()
            val senha = binding.activityLoginSenha.text.toString()
            autentica(usuario, senha)
        }
    }

    private fun autentica(usuario: String, senha: String) {
        lifecycleScope.launch {
            dao.autentica(usuario, senha).collect {
                it?.let {
                    dataStore.edit { preferences ->
                        preferences[usuarioLogadoPreferences] = it.id
                    }
                    vaiPara(ListaProdutosActivity::class.java)
                    finish()
                } ?: toast("Falha na autenticação")
            }
        }
    }

    private fun configuraBotaoCadastrar() {
        binding.activityLoginBotaoCadastrar.setOnClickListener {
            vaiPara(FormularioCadastroUsuarioActivity::class.java)
        }
    }
}
