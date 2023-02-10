package br.com.vaniala.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.vaniala.orgs.model.Produto

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 10/02/23.
 *
 */
@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun buscaTodos(): List<Produto>

    @Insert
    fun salva(vararg produto: Produto)
}
