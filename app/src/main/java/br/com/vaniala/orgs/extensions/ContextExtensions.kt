package br.com.vaniala.orgs.extensions

import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 12/02/23.
 *
 */
fun Context.vaiPara(clazz: Class<*>, intent: Intent.() -> Unit = {}) {
    Intent(this, clazz)
        .apply {
            intent()
            startActivity(this)
        }
}

fun Context.toast(mensagem: String) {
    android.widget.Toast.makeText(
        this,
        mensagem,
        android.widget.Toast.LENGTH_LONG,
    ).show()
}
