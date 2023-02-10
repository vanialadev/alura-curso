package br.com.vaniala.orgs.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 10/02/23.
 *
 */
class Converters {
    @TypeConverter
    fun deDouble(valor: Double?): BigDecimal {
        return valor?.let { BigDecimal(it.toString()) } ?: BigDecimal.ZERO
    }

    @TypeConverter
    fun bigDecimalParaDouble(valor: BigDecimal?): Double? {
        return valor?.let { valor.toDouble() }
    }
}
