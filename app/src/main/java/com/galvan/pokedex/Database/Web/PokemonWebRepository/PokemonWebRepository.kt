package com.galvan.pokedex.Database.Web.PokemonWebRepository

import com.galvan.pokedex.Data.Domain.Repository.PokemonRepository.PokemonRepository
import com.galvan.pokedex.Database.Web.Events.AllPokemons
import kotlinx.coroutines.flow.MutableStateFlow

class PokemonWebRepository : PokemonRepository {

    override fun getAllPokemons(process: MutableStateFlow<AllPokemons>) {

    }


}