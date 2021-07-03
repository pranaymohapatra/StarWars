package com.starwars.starwarstournament.domain

import android.util.SparseArray
import com.starwars.starwarstournament.common.dto.matchdetails.MatchItem
import com.starwars.starwarstournament.common.dto.matchdetails.MatchesDetailData
import com.starwars.starwarstournament.common.dto.matchdetails.MatchesDetailDataItem
import com.starwars.starwarstournament.common.dto.pointstable.PlayerDataItem
import com.starwars.starwarstournament.common.dto.pointstable.PlayerList
import com.starwars.starwarstournament.helper.Constants
import com.starwars.starwarstournament.helper.ResponseResult
import com.starwars.starwarstournament.helper.getResultData
import com.starwars.starwarstournament.helper.succeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMatchesUseCase @Inject constructor(
    private val playerRepository: Repository<PlayerList, String>,
    private val matchesRepository: Repository<MatchesDetailData, String>
) {
    private val matchList: MutableList<MatchItem> by lazy {
        mutableListOf()
    }

    suspend fun getMatchesList(playerId: Int): ResponseResult<List<MatchItem>> {
        return withContext(Dispatchers.IO) {
            if (matchList.isEmpty()) {
                val deferredPlayers = async {
                    playerRepository.getData(Constants.PLAYER_FILE_NAME)
                }
                val deferredMatches = async {
                    matchesRepository.getData(Constants.MATCHES_FILE_NAME)
                }

                val playersResult = deferredPlayers.await()
                val matchesResult = deferredMatches.await()

                if (playersResult.succeeded && matchesResult.succeeded) {
                    ResponseResult.Success(
                        makeMatchTable(
                            playersResult.getResultData(),
                            matchesResult.getResultData()
                        ).filter { filterWhen(it, playerId) }
                    )
                } else {
                    ResponseResult.Error("Cannot fetch points table")
                }
            } else {
                ResponseResult.Success(matchList.filter { filterWhen(it, playerId) })
            }
        }
    }

    private fun filterWhen(matchItem: MatchItem, playerId: Int): Boolean =
        matchItem.player1Id == playerId || matchItem.player2Id == playerId

    private fun makeMatchTable(
        playerList: List<PlayerDataItem>,
        matchesList: List<MatchesDetailDataItem>
    ): List<MatchItem> {
        val matchList = mutableListOf<MatchItem>()
        val nameSparseArray = SparseArray<String>()
        playerList.forEach {
            nameSparseArray.put(it.id, it.name)
        }
        matchesList.forEach {
            matchList.add(
                MatchItem(
                    id = it.match,
                    player1Name = nameSparseArray[it.player1.id],
                    player1Id = it.player1.id,
                    player1Score = it.player1.score,
                    player2Name = nameSparseArray[it.player2.id],
                    player2Id = it.player2.id,
                    player2Score = it.player2.score
                )
            )
        }
        this.matchList.apply {
            clear()
            addAll(matchList)
        }
        return matchList
    }
}