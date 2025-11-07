package com.galvan.pokedex.Database.SQL.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.galvan.pokedex.Database.SQL.Entity.PokemonEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    suspend fun getAllFlow(): List<PokemonEntity>

    @Query("SELECT * FROM pokemons WHERE id = :id")
    suspend fun getById(id: Int): PokemonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemons: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: PokemonEntity)

    @Query("SELECT * FROM pokemons WHERE favorite = 1 ORDER BY id ASC")
    suspend fun getFavoritePokemons(): List<PokemonEntity>

    @Query("UPDATE pokemons SET `favorite` = 1 WHERE id = :id")
    suspend fun setFavoritePokemon(id:Int)

    @Query("UPDATE pokemons SET `favorite` = 0 WHERE id = :id")
    suspend fun unFavoritePokemon(id: Int)

    @Query("DELETE FROM pokemons")
    suspend fun clearAll()

}