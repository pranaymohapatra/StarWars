package com.starwars.starwarstournament.domain

import android.util.SparseIntArray
import com.starwars.starwarstournament.common.dto.matchdetails.MatchesDetailData
import com.starwars.starwarstournament.common.dto.matchdetails.MatchesDetailDataItem
import com.starwars.starwarstournament.common.dto.pointstable.PlayerDataItem
import com.starwars.starwarstournament.common.dto.pointstable.PlayerList
import com.starwars.starwarstournament.common.dto.pointstable.PointsItem
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
class GetPointsUseCase @Inject constructor(
    private val playerRepository: Repository<PlayerList, String>,
    private val matchesRepository: Repository<MatchesDetailData, String>
) {

    suspend fun getPointsTableSorted(): ResponseResult<List<PointsItem>> {
        return withContext(Dispatchers.IO) {
            val defferedPlayers = async {
                playerRepository.getData(Constants.PLAYER_FILE_NAME)
            }
            val deferredMatches = async {
                matchesRepository.getData(Constants.MATCHES_FILE_NAME)
            }

            val playersResult = defferedPlayers.await()
            val matchesResult = deferredMatches.await()

            if (playersResult.succeeded && matchesResult.succeeded) {
                ResponseResult.Success(
                    makePointsTable(
                        playersResult.getResultData(),
                        matchesResult.getResultData()
                    )
                )
            } else {
                ResponseResult.Error("Cannot fetch points table")
            }
        }
    }

    private fun makePointsTable(
        playerList: List<PlayerDataItem>,
        matchesList: List<MatchesDetailDataItem>
    ): List<PointsItem> {
        val pointsList = mutableListOf<PointsItem>()
        val mapOfPlayers = SparseIntArray()
        matchesList.forEach {
            var player1Points = mapOfPlayers[it.player1.id, 0]
            var player2Points = mapOfPlayers[it.player2.id, 0]

            if (it.player1.score > it.player2.score)
                player1Points += 3
            else if (it.player1.score == it.player2.score) {
                ++player1Points
                ++player2Points
            }
            mapOfPlayers.put(it.player1.id, player1Points)
            mapOfPlayers.put(it.player2.id, player2Points)
        }

        playerList.forEach {
            pointsList.add(
                PointsItem(
                    points = mapOfPlayers[it.id],
                    id = it.id,
                    name = it.name,
                    icon = it.icon
                )
            )
        }
        return pointsList
    }
}