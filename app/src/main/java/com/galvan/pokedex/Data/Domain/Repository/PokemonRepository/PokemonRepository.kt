package com.galvan.pokedex.Data.Domain.Repository.PokemonRepository

import com.galvan.pokedex.Database.Web.Events.AllPokemons
import kotlinx.coroutines.flow.MutableStateFlow

interface PokemonRepository {

    fun getAllPokemons(process: MutableStateFlow<AllPokemons>)
}