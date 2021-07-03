package com.starwars.starwarstournament.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActivityModule {

    @Provides
    @Singleton
    fun gson(): Gson {
        return Gson()
    }

}