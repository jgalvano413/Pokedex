package com.galvan.pokedex.Data.Domain.Repository.LocalRepository

import com.galvan.pokedex.Database.SQL.Entity.PokemonEntity

interface PokemonLocalRepository {

    suspend fun getAllFlow(): List<PokemonEntity>

    suspend fun getById(id: Int): PokemonEntity?

    suspend fun insertAll(pokemons: List<PokemonEntity>)


    suspend fun insert(pokemon: PokemonEntity)

    suspend fun getFavoritePokemons(): List<PokemonEntity>


    suspend fun setFavoritePokemon(id:Int)


    suspend fun unFavoritePokemon(id: Int)

    suspend fun clearAll()
}