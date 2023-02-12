package br.com.vaniala.orgs.extensions

import android.content.Context
import android.content.Intent

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 12/02/23.
 *
 */
fun Context.vaiPara(clazz: Class<*>) {
    Intent(this, clazz)
        .apply {
            startActivity(this)
        }
}
