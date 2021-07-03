package com.starwars.starwarstournament.common.dto.matchdetails

data class MatchItem(
    val id: Int,
    val player1Name: String,
    val player1Id: Int,
    val player1Score: Int,
    val player2Name: String,
    val player2Id: Int,
    val player2Score: Int
) : Comparable<MatchItem> {
    override fun compareTo(other: MatchItem): Int {
        return when {
            id < other.id -> -1
            id > other.id -> 1
            else -> 0
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is MatchItem
                && id == other.id
                && player1Id == other.player1Id
                && player2Id == other.player2Id
    }
}