package com.galvan.pokedex.Activitys.SplashActivity

import androidx.lifecycle.ViewModel
import com.galvan.pokedex.Data.Domain.AppDataStore.AppDataStore
import com.galvan.pokedex.Database.SQL.DatabaseRoom.DatabaseRoom
import com.galvan.pokedex.Database.Web.Events.AllPokemons
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class SplashActivityViewModel @Inject constructor(
    private val appDataStore: AppDataStore
) : ViewModel() {

    val state: StateFlow<AllPokemons> get() = appDataStore.stateDownload
}