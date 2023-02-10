package br.com.vaniala.orgs.ui.recyclerview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.vaniala.orgs.databinding.ProdutoItemBinding
import br.com.vaniala.orgs.extensions.formataParaMoedaBrasileira
import br.com.vaniala.orgs.extensions.tentaCarregarImagem
import br.com.vaniala.orgs.model.Produto
import java.util.*

class ListaProdutosAdapter(
    private val context: Context,
    produtos: List<Produto>,
    var quandoClicaNoItem: (produto: Produto) -> Unit = {},
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()

    inner class ViewHolder(binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var produto: Produto

        private val imagem = binding.produtoItemImagem
        private val nome = binding.produtoItemNome
        private val descricao = binding.produtoItemDescricao
        private val valor = binding.produtoItemValor

        init {
            itemView.setOnClickListener {
                if (::produto.isInitialized) {
                    quandoClicaNoItem(produto)
                }
            }
        }

        fun vincula(produto: Produto, context: Context) {
            this.produto = produto

            val visibilidade = if (produto.imagem != null) {
                View.VISIBLE
            } else {
                View.GONE
            }

            imagem.visibility = visibilidade

            imagem.tentaCarregarImagem(produto.imagem, context)
            nome.text = produto.nome
            descricao.text = produto.descricao
            val valorEmMoeda: String = produto.valor.formataParaMoedaBrasileira()
            valor.text = valorEmMoeda
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProdutoItemBinding
            .inflate(
                LayoutInflater.from(context),
                parent,
                false,
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto, context)
    }

    override fun getItemCount() = produtos.size

    @SuppressLint("NotifyDataSetChanged")
    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }
}
