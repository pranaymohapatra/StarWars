package com.starwars.starwarstournament.common.dto.pointstable

data class PointsItem(val points: Int, val id: Int, val name: String, val icon: String) :
    Comparable<PointsItem> {
    override fun compareTo(other: PointsItem): Int {
        return when {
            points < other.points -> -1
            points > other.points -> 1
            else -> 0
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is PointsItem
                && id == other.id
                && icon == other.icon
                && name == other.name
                && points == other.points
    }
}