package br.com.vaniala.orgs.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.vaniala.orgs.database.AppDatabase
import br.com.vaniala.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.vaniala.orgs.extensions.tentaCarregarImagem
import br.com.vaniala.orgs.model.Produto
import br.com.vaniala.orgs.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 19/10/22.
 *
 */
class FormularioProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var idProduto = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoSalvar()
        title = "Cadastrar produto"
        tentaCarregarProduto()

        binding.activityFormularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this).mostra(url) { imagem ->
                url = imagem
                binding.activityFormularioProdutoImagem.tentaCarregarImagem(url, this)
            }
        }
    }

    private fun tentaCarregarProduto() {
        if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(CHAVE_PRODUTO, Produto::class.java)?.let { produtoCarregado ->
                carregaDadosProdutos(produtoCarregado)
            }
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
                carregaDadosProdutos(produtoCarregado)
            }
        }
    }

    private fun carregaDadosProdutos(produtoCarregado: Produto) {
        title = "Alterar produto"
        idProduto = produtoCarregado.id
        url = produtoCarregado.imagem
        binding.activityFormularioProdutoImagem.tentaCarregarImagem(produtoCarregado.imagem, this)
        binding.activityFormularioProdutoNome.setText(produtoCarregado.nome)
        binding.activityFormularioProdutoDescricao.setText(produtoCarregado.descricao)
        binding.activityFormularioProdutoValor.setText(produtoCarregado.valor.toPlainString())
    }

    private fun configuraBotaoSalvar() {
        val db = AppDatabase.instancia(this)
        val dao = db.produtoDao()
        binding.activityFormularioProdutoBotaoSalvar.setOnClickListener {
            val produto = criaProduto()
            if (idProduto > 0) {
                dao.altera(produto)
            } else {
                dao.salva(produto)
            }
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val nome = binding.activityFormularioProdutoNome.text.toString()
        val descricao = binding.activityFormularioProdutoDescricao.text.toString()
        val valorEmTexto = binding.activityFormularioProdutoValor.text.toString()
        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            id = idProduto,
            imagem = url,
            nome = nome,
            descricao = descricao,
            valor = valor,
        )
    }
}
