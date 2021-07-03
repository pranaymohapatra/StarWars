package com.starwars.starwarstournament.di

import android.content.Context
import com.starwars.starwarstournament.presentation.view.BaseFragment
import com.starwars.starwarstournament.presentation.view.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, ViewModelModule::class, ActivityModule::class])
interface StarWarsComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(baseFragment: BaseFragment)


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun activityContext(context: Context): Builder

        fun build(): StarWarsComponent
    }
}