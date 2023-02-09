package br.com.vaniala.orgs.dao

import br.com.vaniala.orgs.model.Produto
import java.math.BigDecimal

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
        private val produtos = mutableListOf<Produto>(
            Produto(
                imagem = "https://cdn.pixabay.com/animation/2022/10/11/09/05/09-05-26-529_512.gif",
                nome = "Salada de frutas",
                descricao = "Laranja, maçã, uva, manga",
                valor = BigDecimal("19.83")
            ),  Produto(
                imagem = "https://i.pinimg.com/originals/b4/e2/f9/b4e2f9968b11e0413f5d9e55c1ed9132.gif",
                nome = "Banana",
                descricao = "banana sambista",
                valor = BigDecimal("1000000000.99")
            ),

        )
    }
}