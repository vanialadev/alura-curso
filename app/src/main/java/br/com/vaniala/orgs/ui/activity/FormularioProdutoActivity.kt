package br.com.vaniala.orgs.ui.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import br.com.vaniala.orgs.database.AppDatabase
import br.com.vaniala.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.vaniala.orgs.extensions.tentaCarregarImagem
import br.com.vaniala.orgs.model.Produto
import br.com.vaniala.orgs.ui.dialog.FormularioImagemDialog
import kotlinx.coroutines.flow.filterNotNull
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
        lifecycleScope.launch {
            usuario
                .filterNotNull()
                .collect {
                }
        }
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
            title = "Alterar produto"
            produto.collect {
                it?.let {
                    carregaDadosProdutos(it)
                }
            }
        }
    }

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
        binding.activityFormularioProdutoBotaoSalvar.setOnClickListener {
            lifecycleScope.launch {
                usuario.value?.let {
                    val produto = criaProduto(it.id)
                    dao.salva(produto)
                    finish()
                }
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
