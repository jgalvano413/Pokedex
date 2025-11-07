package com.galvan.pokedex.Database.Web.RetroFit.Remote

import com.galvan.pokedex.Data.Domain.Model.PokemonDetail
import com.galvan.pokedex.Data.Domain.Model.PokemonListResponse
import com.galvan.pokedex.Database.Web.RetroFit.ServiceApiRest.ServiceApiRest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pokeApi: ServiceApiRest = retrofit.create(ServiceApiRest::class.java)

}