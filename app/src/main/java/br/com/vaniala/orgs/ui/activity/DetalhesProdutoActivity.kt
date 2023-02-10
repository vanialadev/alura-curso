package br.com.vaniala.orgs.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.vaniala.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.vaniala.orgs.extensions.formataParaMoedaBrasileira
import br.com.vaniala.orgs.extensions.tentaCarregarImagem
import br.com.vaniala.orgs.model.Produto

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 09/02/23.
 *
 */
class DetalhesProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    private fun tentaCarregarProduto() {
        if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(CHAVE_PRODUTO, Produto::class.java)?.let { produtoCarregado ->
                preencheCampos(produtoCarregado)
            } ?: finish()
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
                preencheCampos(produtoCarregado)
            } ?: finish()
        }
    }

    private fun preencheCampos(produtoCarregado: Produto) {
        with(binding) {
            activityDetalhesProdutoImagem.tentaCarregarImagem(
                produtoCarregado.imagem,
                this@DetalhesProdutoActivity
            )
            activityDetalhesProdutoNome.text = produtoCarregado.nome
            activityDetalhesProdutoDescricao.text = produtoCarregado.descricao
            activityDetalhesProdutoValor.text =
                produtoCarregado.valor.formataParaMoedaBrasileira()
        }
    }

}