package com.galvan.pokedex.Database.Web.RetroFit.ServiceApiRest

import com.galvan.pokedex.Data.Domain.Model.PokemonDetail
import com.galvan.pokedex.Data.Domain.Model.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ServiceApiRest {

    @GET("pokemon")
    suspend fun getPokemonPage(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonListResponse>

    @GET("pokemon/{id}/")
    suspend fun getPokemon(
        @Path("id") id: String,
    ): Response<PokemonDetail>

    @GET
    suspend fun getPokemonByUrl(@Url url: String): Response<PokemonDetail>

}