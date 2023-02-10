package br.com.vaniala.orgs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.vaniala.orgs.database.converter.Converters
import br.com.vaniala.orgs.database.dao.ProdutoDao
import br.com.vaniala.orgs.model.Produto

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 10/02/23.
 *
 */
@Database(entities = [Produto::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao
}
