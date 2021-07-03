package com.starwars.starwarstournament.common.dto.pointstable


import com.google.gson.annotations.SerializedName

data class PlayerDataItem(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)