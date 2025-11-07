package com.galvan.pokedex.Data.Domain.AppDataStore

import com.galvan.pokedex.Data.Domain.Model.PokemonDetail
import com.galvan.pokedex.Data.Domain.Model.PokemonListResponse
import com.galvan.pokedex.Database.SQL.DatabaseRoom.DatabaseRoom
import com.galvan.pokedex.Database.SQL.Entity.PokemonEntity
import com.galvan.pokedex.Database.Web.Events.AllPokemons
import com.galvan.pokedex.Database.Web.Events.getPokemon
import com.galvan.pokedex.Database.Web.PokemonWebRepository.PokemonWebRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class AppDataStore @Inject constructor(
    private val databaseRoom: DatabaseRoom,
    private val pokemonWebRepository: PokemonWebRepository
) : CoroutineScope {

    private var job = SupervisorJob()
    override val coroutineContext: CoroutineContext get() = Dispatchers.IO + job
    private val Scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val _stateDownload = MutableStateFlow<AllPokemons>(AllPokemons.IDle)
    val stateDownload : StateFlow<AllPokemons> get() = _stateDownload
    var pokemonsList = mutableListOf<PokemonEntity>()

    init {
        launch {
            hasInformationLocal()
        }
    }

    private suspend fun hasInformationLocal(){
        val local = databaseRoom.getAllFlow()
        if (local.isEmpty()) downloadPokemons()
        else {
            _stateDownload.value = AllPokemons.readyLocall
            pokemonsList.addAll(local)
        }
    }

    private suspend fun downloadPokemons(){
        pokemonWebRepository.getAllPokemons(_stateDownload)
        collectState()
    }

    private suspend fun collectState(){
        stateDownload.collect { result ->
            when(result){
                AllPokemons.Failed.otherError -> { }
                AllPokemons.Failed.serverError -> { }
                AllPokemons.Failed.timeOut -> { }
                AllPokemons.IDle -> { }
                AllPokemons.Loading -> { }

                is AllPokemons.Succesful -> saveLocalPokemons(result.pokemons)

                AllPokemons.readyLocall -> { }

                AllPokemons.savedatabaseLocal -> { }
            }
        }
    }

    private suspend fun saveLocalPokemons(pokemonListResponse: PokemonListResponse){
        _stateDownload.value = AllPokemons.savedatabaseLocal
        val deferredEntities = pokemonListResponse.results.map { pokemonsList ->
            Scope.async {
                returnPokemon(pokemonsList.url)
            }
        }
        val list = deferredEntities.awaitAll().filterNotNull()
        databaseRoom.insertAll(list)
        pokemonsList = list.toMutableList()
        _stateDownload.value = AllPokemons.readyLocall
    }

    private suspend fun returnPokemon(url:String): PokemonEntity? {
        var pokemon: PokemonEntity? = null
        val _download = MutableStateFlow<getPokemon>(getPokemon.IDle)
        pokemonWebRepository.getPokemon(url,_download)
        _download.collect { result ->
            when(result){
                getPokemon.Failed.otherError -> { }
                getPokemon.Failed.serverError -> { }
                getPokemon.Failed.timeOut -> { }
                getPokemon.IDle -> { }
                getPokemon.Loading -> { }
                is getPokemon.Succesful -> pokemon = getPokemonEntiity(result.pokemon)
            }
        }
        return pokemon
    }

    private fun getPokemonEntiity(poke: PokemonDetail): PokemonEntity {
        return PokemonEntity(
            id = poke.id,
            name = poke.name,
            baseExperience = poke.base_experience,
            height = poke.height,
            weight = poke.weight,
            imageUrl = poke.sprites.toString(),
            typesJson = poke.types.toString(),
            statsJson = poke.stats.toString()
        )
    }
}