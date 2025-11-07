package com.galvan.pokedex.Database.SQL.DatabaseHelper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.galvan.pokedex.Database.SQL.Dao.PokemonDao
import com.galvan.pokedex.Database.SQL.Entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
abstract class AccesoDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object{
        @Volatile
        private var INSTANCE: AccesoDatabase? = null

        fun getInstance(context: Context): AccesoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AccesoDatabase::class.java,
                    "AccesoDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }

}