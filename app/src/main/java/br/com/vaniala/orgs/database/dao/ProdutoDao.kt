package br.com.vaniala.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.vaniala.orgs.model.Produto
import java.math.BigDecimal

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 10/02/23.
 *
 */
@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    suspend fun buscaTodos(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY nome DESC")
    suspend fun buscaTodosOrdenadorPorNomeDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY nome ASC")
    suspend fun buscaTodosOrdenadorPorNomeAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY descricao DESC")
    suspend fun buscaTodosOrdenadorPorDescricaoDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY descricao ASC")
    suspend fun buscaTodosOrdenadorPorDescricaoAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor DESC")
    suspend fun buscaTodosOrdenadorPorValorDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor ASC")
    suspend fun buscaTodosOrdenadorPorValorAsc(): List<Produto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(vararg produto: Produto)

    @Delete
    suspend fun deleta(produto: Produto)

    @Query("SELECT * FROM Produto WHERE id = :id")
    suspend fun buscaPorId(id: Long): Produto?

    companion object {
        private val produtos = mutableListOf<Produto>(
            Produto(
                imagem = "https://cdn.pixabay.com/animation/2022/10/11/09/05/09-05-26-529_512.gif",
                nome = "Abacate",
                descricao = "Abacate gordinho",
                valor = BigDecimal("19.83"),
            ),
            Produto(
                imagem = "https://i.pinimg.com/originals/b4/e2/f9/b4e2f9968b11e0413f5d9e55c1ed9132.gif",
                nome = "Banana",
                descricao = "banana sambista",
                valor = BigDecimal("1000000000.99"),
            ),
            Produto(
                imagem = "https://gifs.eco.br/wp-content/uploads/2021/09/gifs-animados-de-maca-16.gif",
                nome = "Maçã",
                descricao = "maçã dando tchau",
                valor = BigDecimal("55.99"),
            ),
        )
    }
}
