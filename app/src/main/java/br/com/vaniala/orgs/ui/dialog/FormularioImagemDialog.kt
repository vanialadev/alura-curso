package br.com.vaniala.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.vaniala.orgs.databinding.FormularioImagemBinding
import br.com.vaniala.orgs.extensions.tentaCarregarImagem

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 09/02/23.
 *
 */
class FormularioImagemDialog(private val context: Context) {

    fun invoke() {
        val binding = FormularioImagemBinding.inflate(LayoutInflater.from(context))

        binding.formularioImagemBotaoCarregar.setOnClickListener {
            val url = binding.formularioImagemUrl.text.toString()
            binding.formularioImagemImageview.tentaCarregarImagem(url, context)
        }

        AlertDialog.Builder(context)
            .setView(binding.root)
            .setPositiveButton("Confirmar") { _, _ ->
                val url = binding.formularioImagemUrl.text.toString()
//                binding.activityFormularioProdutoImagem.tentaCarregarImagem(url, context)
            }
            .setNegativeButton("Cancelar") { _, _ -> }
            .show()
    }

}