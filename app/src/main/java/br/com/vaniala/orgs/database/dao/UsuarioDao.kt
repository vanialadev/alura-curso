package br.com.vaniala.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.vaniala.orgs.model.Usuario
import kotlinx.coroutines.flow.Flow

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 12/02/23.
 *
 */

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(vararg usuario: Usuario)

    @Query(
        """ 
        SELECT * FROM Usuario  
        WHERE id = :usuarioId  
        AND senha = :senha""",
    )
    fun autentica(
        usuarioId: String,
        senha: String,
    ): Flow<Usuario?>

    @Query("SELECT * FROM Usuario WHERE id = :usuarioId")
    fun buscaPorId(usuarioId: String): Flow<Usuario>

    @Query("SELECT * FROM Usuario")
    fun buscaTodos(): Flow<List<Usuario>>
}
