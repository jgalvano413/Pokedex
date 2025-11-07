package com.galvan.pokedex.Database.Web.Events

import com.galvan.pokedex.Data.Domain.Model.PokemonListResponse

sealed class AllPokemons {

    data class Succesful(val pokemons: PokemonListResponse) : AllPokemons()
    object Loading : AllPokemons()
    object savedatabaseLocal : AllPokemons()
    object readyLocall : AllPokemons()
    object IDle : AllPokemons()
    sealed class Failed : AllPokemons() {
        object otherError : Failed()
        object serverError : Failed()
        object timeOut : Failed()
    }
}