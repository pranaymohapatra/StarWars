package com.starwars.starwarstournament.common.dto.matchdetails


import com.google.gson.annotations.SerializedName

data class MatchesDetailDataItem(
    @SerializedName("match")
    val match: Int,
    @SerializedName("player1")
    val player1: Player,
    @SerializedName("player2")
    val player2: Player
)