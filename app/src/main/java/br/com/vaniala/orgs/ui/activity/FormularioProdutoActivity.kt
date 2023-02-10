package br.com.vaniala.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.vaniala.orgs.dao.ProdutoDao
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoSalvar()
        title = "Cadastrar produto"

        binding.activityFormularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this).mostra(url) { imagem ->
                url = imagem
                binding.activityFormularioProdutoImagem.tentaCarregarImagem(url, this)
            }
        }
    }

    private fun configuraBotaoSalvar() {
        val dao = ProdutoDao()
        binding.activityFormularioProdutoBotaoSalvar.setOnClickListener {
            val produto = criaProduto()
            dao.adiciona(produto)
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
            imagem = url,
            nome = nome,
            descricao = descricao,
            valor = valor
        )
    }
}
