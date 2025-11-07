package com.galvan.pokedex.Database.Web.Events

import com.galvan.pokedex.Data.Domain.Model.PokemonDetail
import com.galvan.pokedex.Data.Domain.Model.PokemonListResponse

sealed class getPokemon {

    data class Succesful(val pokemon: PokemonDetail) : getPokemon()
    object Loading : getPokemon()
    object IDle : getPokemon()
    sealed class Failed : getPokemon() {
        object otherError : Failed()
        object serverError : Failed()
        object timeOut : Failed()
    }
}