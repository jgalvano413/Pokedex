package com.galvan.pokedex.Database.Web.Events

sealed class AllPokemons {

    object Succesful : AllPokemons()
    object Loading : AllPokemons()
    object IDle : AllPokemons()
    sealed class Failed : AllPokemons() {
        object networoError : Failed()
        object serverError : Failed()
        object timeOut : Failed()
    }
}