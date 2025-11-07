package com.galvan.pokedex.Database.Web.PokemonWebRepository

import com.galvan.pokedex.Data.Domain.Repository.PokemonRepository.PokemonRepository
import com.galvan.pokedex.Database.Web.Events.AllPokemons
import com.galvan.pokedex.Database.Web.Events.getPokemon
import com.galvan.pokedex.Database.Web.RetroFit.Remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow

class PokemonWebRepository : PokemonRepository {

    override suspend fun getAllPokemons(process: MutableStateFlow<AllPokemons>) {
        try {
            process.value = AllPokemons.Loading
            val response = RetrofitClient.pokeApi.getPokemonPage(250, 0)
            if (response.isSuccessful) {
                val pokemons = response.body()
                pokemons?.let {
                    process.value = AllPokemons.Succesful(pokemons)
                } ?: run {
                    process.value = AllPokemons.Failed.otherError
                }
            } else process.value = AllPokemons.Failed.serverError
        } catch (e: Exception){
            e.printStackTrace()
            process.value = AllPokemons.Failed.otherError
        }
    }

    override suspend fun getPokemon(url: String, process: MutableStateFlow<getPokemon>) {
        try {
            process.value = getPokemon.Loading
            val response = RetrofitClient.pokeApi.getPokemonByUrl(url)
            if (response.isSuccessful){
                val pokemon = response.body()
                pokemon?.let {
                    process.value = getPokemon.Succesful(it)
                } ?: run {
                    process.value = getPokemon.Failed.otherError
                }
            } else process.value = getPokemon.Failed.serverError
        } catch (e: Exception){
            e.printStackTrace()
            process.value = getPokemon.Failed.otherError
        }
    }


}