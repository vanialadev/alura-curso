package br.com.vaniala.orgs.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.vaniala.orgs.database.AppDatabase
import br.com.vaniala.orgs.databinding.ActivityFormularioCadastroUsuarioBinding
import br.com.vaniala.orgs.extensions.toast
import br.com.vaniala.orgs.model.Usuario
import kotlinx.coroutines.launch

class FormularioCadastroUsuarioActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioCadastroUsuarioBinding.inflate(layoutInflater)
    }

    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()
    }

    private fun configuraBotaoCadastrar() {
        binding.activityFormularioCadastroBotaoCadastrar.setOnClickListener {
            val novoUsuario = criaUsuario()
            cadastra(novoUsuario)
        }
    }

    private fun cadastra(usuario: Usuario) {
        lifecycleScope.launch {
            try {
                usuarioDao.salva(usuario)
                finish()
            } catch (e: Exception) {
                Log.e("Cadastro usuário", "configuraBotaoCadastrar: ", e)
                toast("Falha ao cadastrar o usuário")
            }
        }
    }

    private fun criaUsuario(): Usuario {
        val usuario = binding.activityFormularioCadastroUsuario.text.toString()
        val nome = binding.activityFormularioCadastroNome.text.toString()
        val senha = binding.activityFormularioCadastroSenha.text.toString()
        return Usuario(usuario, nome, senha)
    }
}
