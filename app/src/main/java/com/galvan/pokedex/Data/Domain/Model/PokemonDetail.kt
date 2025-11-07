package com.galvan.pokedex.Data.Domain.Model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val base_experience: Int?,
    val height: Int?,
    val weight: Int?,
    val types: List<TypeSlot> = emptyList(),
    val stats: List<StatSlot> = emptyList(),
    val sprites: Sprites?
)