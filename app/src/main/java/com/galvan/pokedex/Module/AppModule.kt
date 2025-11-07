package com.galvan.pokedex.Module

import android.content.Context
import com.galvan.pokedex.Database.SQL.DatabaseRoom.DatabaseRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun databaseRoom(@ApplicationContext context: Context): DatabaseRoom {
        return DatabaseRoom(context)
    }




}