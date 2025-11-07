package com.galvan.pokedex.Database.SQL.DatabaseRoom

import android.content.Context
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.galvan.pokedex.Data.Domain.Repository.LocalRepository.PokemonLocalRepository
import com.galvan.pokedex.Database.SQL.DatabaseHelper.AccesoDatabase.Companion.getInstance
import com.galvan.pokedex.Database.SQL.Entity.PokemonEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DatabaseRoom @Inject constructor(
    @ApplicationContext private val context: Context
) : PokemonLocalRepository {

    private val pokemonDao = getInstance(context).pokemonDao()

    override suspend fun getAllFlow(): List<PokemonEntity> {
        return pokemonDao.getAllFlow()
    }

    override suspend fun getById(id: Int): PokemonEntity? {
        return pokemonDao.getById(id)
    }


    override suspend fun insertAll(pokemons: List<PokemonEntity>) {
        pokemonDao.insertAll(pokemons)
    }


    override suspend fun insert(pokemon: PokemonEntity) {
        pokemonDao.insert(pokemon)
    }

    override suspend fun getFavoritePokemons(): List<PokemonEntity> {
        return pokemonDao.getFavoritePokemons()
    }


    override suspend fun setFavoritePokemon(id:Int) {
        pokemonDao.setFavoritePokemon(id)
    }


    override suspend fun unFavoritePokemon(id: Int) {
        pokemonDao.unFavoritePokemon(id)
    }

    override suspend fun clearAll() {
        pokemonDao.clearAll()
    }

}