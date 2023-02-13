package br.com.vaniala.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import br.com.vaniala.orgs.model.Usuario

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 12/02/23.
 *
 */

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(vararg usuario: Usuario)
}
