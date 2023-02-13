package br.com.vaniala.orgs.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 12/02/23.
 *
 */
@Entity
@Parcelize
data class Usuario(
    @PrimaryKey
    val id: String,
    val nome: String,
    val senha: String,
) : Parcelable
