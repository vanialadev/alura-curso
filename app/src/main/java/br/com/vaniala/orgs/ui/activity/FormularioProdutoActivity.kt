package br.com.vaniala.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import br.com.vaniala.orgs.R
import br.com.vaniala.orgs.dao.ProdutoDao
import br.com.vaniala.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.vaniala.orgs.model.Produto
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoSalvar()
        binding.activityFormularioProdutoImagem.setOnClickListener {
            AlertDialog.Builder(this)
                .setView(R.layout.activity_formulario_imagem)
                .setPositiveButton("Confirmar", {_,_ ->

                })
                .setNegativeButton("Cancelar",{_,_ -> })
                .show()
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
            nome = nome,
            descricao = descricao,
            valor = valor
        )
    }
}
