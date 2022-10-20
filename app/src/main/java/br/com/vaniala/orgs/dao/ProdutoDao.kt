package br.com.vaniala.orgs.dao

import br.com.vaniala.orgs.model.Produto

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 19/10/22.
 *
 */
class ProdutoDao {

    fun adiciona(produto: Produto) {
        produtos.add(produto)
    }

    fun buscaTodos(): List<Produto> {
        return produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf<Produto>()
    }
}