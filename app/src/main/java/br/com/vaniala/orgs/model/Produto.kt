package br.com.vaniala.orgs.model

import java.math.BigDecimal

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 19/10/22.
 *
 */
data class  Produto(
    val nome: String,
    val descricao: String,
    val valor: BigDecimal
)
