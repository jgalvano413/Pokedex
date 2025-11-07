package com.galvan.pokedex.Database.SQL.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val baseExperience: Int?,
    val height: Int?,
    val weight: Int?,
    val favorite: Int = 0,
    val imageUrl: String?,
    val typesJson: String?,
    val statsJson: String?
)