package br.com.vaniala.orgs.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 19/10/22.
 *
 */
@Entity
@Parcelize
data class Produto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val imagem: String? = null,
    val nome: String,
    val descricao: String,
    val valor: BigDecimal,
    val usuarioId: String? = null,
) : Parcelable {
    fun salvoSemUsuario() = usuarioId.isNullOrBlank() &&
        id > 0L
}
