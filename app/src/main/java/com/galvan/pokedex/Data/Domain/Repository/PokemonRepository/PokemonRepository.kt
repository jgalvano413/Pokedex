package com.galvan.pokedex.Data.Domain.Repository.PokemonRepository

import com.galvan.pokedex.Database.Web.Events.AllPokemons
import com.galvan.pokedex.Database.Web.Events.getPokemon
import kotlinx.coroutines.flow.MutableStateFlow

interface PokemonRepository {

    suspend fun getAllPokemons(process: MutableStateFlow<AllPokemons>)


    suspend fun getPokemon(url:String,process: MutableStateFlow<getPokemon>)
}