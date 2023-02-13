package br.com.vaniala.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.vaniala.orgs.database.converter.Converters
import br.com.vaniala.orgs.database.dao.ProdutoDao
import br.com.vaniala.orgs.database.dao.UsuarioDao
import br.com.vaniala.orgs.model.Produto
import br.com.vaniala.orgs.model.Usuario

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 10/02/23.
 *
 */
@Database(
    entities = [
        Produto::class,
        Usuario::class,
    ],
    version = 2,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {

        @Volatile
        private lateinit var db: AppDatabase

        fun instancia(context: Context): AppDatabase {
            if (::db.isInitialized) return db
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "orgs.db",
            ).addMigrations(MIGRATION_1_2)
                .build().also {
                    db = it
                }
        }
    }
}
