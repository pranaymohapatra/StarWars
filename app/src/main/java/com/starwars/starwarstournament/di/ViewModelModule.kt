package com.starwars.starwarstournament.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.starwars.starwarstournament.presentation.viewmodel.MatchesDetailViewModel
import com.starwars.starwarstournament.presentation.viewmodel.PointsTableViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @Singleton
    abstract fun getVMFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory


    @IntoMap
    @ViewModelKey(MatchesDetailViewModel::class)
    @Binds
    @Singleton
    abstract fun getMatchesVM(matchesVM: MatchesDetailViewModel): ViewModel

    @IntoMap
    @ViewModelKey(PointsTableViewModel::class)
    @Binds
    @Singleton
    abstract fun getPointsVM(pointsVM: PointsTableViewModel): ViewModel

}