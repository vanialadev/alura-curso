package br.com.vaniala.orgs.ui.activity

import android.R
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import br.com.vaniala.orgs.database.AppDatabase
import br.com.vaniala.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.vaniala.orgs.extensions.tentaCarregarImagem
import br.com.vaniala.orgs.model.Produto
import br.com.vaniala.orgs.model.Usuario
import br.com.vaniala.orgs.ui.dialog.FormularioImagemDialog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.math.BigDecimal

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 19/10/22.
 *
 */
class FormularioProdutoActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var produtoId = 0L

    private val dao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"
        configuraImageview()
        configuraBotaoSalvar()
        tentaCarregarProduto()
    }

    private fun configuraImageview() {
        binding.activityFormularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this).mostra(url) { imagem ->
                url = imagem
                binding.activityFormularioProdutoImagem.tentaCarregarImagem(url, this)
            }
        }
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    private fun tentaBuscarProduto() {
        lifecycleScope.launch {
            val produto = dao.buscaPorId(produtoId)
            produto.collect {
                it?.let {
                    title = "Alterar produto"
                    val campoUsuarioId =
                        binding.activityFormularioProdutoUsuarioId
                    campoUsuarioId.visibility =
                        if (it.salvoSemUsuario()) {
                            configuraCampoUsuario()
                            VISIBLE
                        } else {
                            GONE
                        }
                    preencheCampos(it)
                }
            }
        }
    }

    private fun configuraCampoUsuario() {
        lifecycleScope.launch {
            usuarios()
                .map { usuarios -> usuarios.map { it.id } }
                .collect { usuarios ->
                    configuraAutoCompleteTextView(usuarios)
                }
        }
    }

    private fun configuraAutoCompleteTextView(
        usuarios: List<String>,
    ) {
        val campoUsuarioId =
            binding.activityFormularioProdutoUsuarioId
        val adapter = ArrayAdapter(
            this@FormularioProdutoActivity,
            R.layout.simple_list_item_1,
            usuarios,
        )
        campoUsuarioId.setAdapter(adapter)
        campoUsuarioId.setOnFocusChangeListener { _, focado ->
            if (!focado) {
                usuarioExistenteValido(usuarios)
            }
        }
    }

    private fun usuarioExistenteValido(
        usuarios: List<String>,
    ): Boolean {
        val campoUsuarioId = binding.activityFormularioProdutoUsuarioId
        val usuarioId = campoUsuarioId.text.toString()
        if (!usuarios.contains(usuarioId)) {
            campoUsuarioId.error = "usuário inexistente!"
            return false
        }
        return true
    }

    private fun preencheCampos(produto: Produto) {
        url = produto.imagem
        binding.activityFormularioProdutoImagem
            .tentaCarregarImagem(produto.imagem, this)
        binding.activityFormularioProdutoNome
            .setText(produto.nome)
        binding.activityFormularioProdutoDescricao
            .setText(produto.descricao)
        binding.activityFormularioProdutoValor
            .setText(produto.valor.toPlainString())
    }

    private suspend fun tentaSalvarProduto() {
        usuario.value?.let { usuario ->
            try {
                val usuarioId = defineUsuarioId(usuario)
                val produto = criaProduto(usuarioId)
                dao.salva(produto)
                finish()
            } catch (e: RuntimeException) {
                Log.e("FormularioProduto", "configuraBotaoSalvar: ", e)
            }
        }
    }

    private suspend fun defineUsuarioId(usuario: Usuario): String = dao
        .buscaPorId(produtoId)
        .first()?.let { produtoEncontrado ->
        if (produtoEncontrado.usuarioId.isNullOrBlank()) {
            val usuarios = usuarios()
                .map { usuariosEncontrados ->
                    usuariosEncontrados.map { it.id }
                }.first()
            if (usuarioExistenteValido(usuarios)) {
                val campoUsuarioId =
                    binding.activityFormularioProdutoUsuarioId
                return campoUsuarioId.text.toString()
            } else {
                throw RuntimeException("Tentou salvar produto com usuário inexistente")
            }
        }
        null
    } ?: usuario.id

    override fun onResume() {
        super.onResume()
        tentaBuscarProduto()
    }

    private fun carregaDadosProdutos(produtoCarregado: Produto) {
        produtoId = produtoCarregado.id
        url = produtoCarregado.imagem
        binding.activityFormularioProdutoImagem.tentaCarregarImagem(produtoCarregado.imagem, this)
        binding.activityFormularioProdutoNome.setText(produtoCarregado.nome)
        binding.activityFormularioProdutoDescricao.setText(produtoCarregado.descricao)
        binding.activityFormularioProdutoValor.setText(produtoCarregado.valor.toPlainString())
    }
    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioProdutoBotaoSalvar
        botaoSalvar.setOnClickListener {
            lifecycleScope.launch {
                tentaSalvarProduto()
            }
        }
    }

    private fun criaProduto(usuarioId: String): Produto {
        val nome = binding.activityFormularioProdutoNome.text.toString()
        val descricao = binding.activityFormularioProdutoDescricao.text.toString()
        val valorEmTexto = binding.activityFormularioProdutoValor.text.toString()
        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            id = produtoId,
            imagem = url,
            nome = nome,
            descricao = descricao,
            valor = valor,
            usuarioId = usuarioId,
        )
    }
}
