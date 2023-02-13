package br.com.vaniala.orgs.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 12/02/23.
 *
 */
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """ 
            CREATE TABLE IF NOT EXISTS `Usuario` ( 
            `id` TEXT NOT NULL,  
            `nome` TEXT NOT NULL,  
            `senha` TEXT NOT NULL, PRIMARY KEY(`id`)) 
            """,
        )
    }
}
