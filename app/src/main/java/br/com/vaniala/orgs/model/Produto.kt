package br.com.vaniala.orgs.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 19/10/22.
 *
 */
@Parcelize
data class Produto(
    val imagem: String? = null,
    val nome: String,
    val descricao: String,
    val valor: BigDecimal
): Parcelable
