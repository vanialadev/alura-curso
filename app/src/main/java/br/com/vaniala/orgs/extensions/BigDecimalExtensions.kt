package br.com.vaniala.orgs.extensions

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 09/02/23.
 *
 */
fun BigDecimal.formataParaMoedaBrasileira(): String {
    val formatador: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
    return formatador.format(this)
}