package com.starwars.starwarstournament.di

import com.starwars.starwarstournament.common.dto.matchdetails.MatchesDetailData
import com.starwars.starwarstournament.common.dto.pointstable.PlayerList
import com.starwars.starwarstournament.data.repository.MatchesRepository
import com.starwars.starwarstournament.data.repository.PlayersRepository
import com.starwars.starwarstournament.domain.Repository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindPlayerRepo(playerRepository: PlayersRepository): Repository<PlayerList, String>

    @Singleton
    @Binds
    abstract fun bindMatchesRepo(matchesRepo: MatchesRepository): Repository<MatchesDetailData, String>

}
