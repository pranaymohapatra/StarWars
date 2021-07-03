package com.starwars.starwarstournament.data.repository

import android.content.Context
import com.google.gson.Gson
import com.starwars.starwarstournament.common.dto.matchdetails.MatchesDetailData
import com.starwars.starwarstournament.data.getJsonFromAssets
import com.starwars.starwarstournament.domain.Repository
import com.starwars.starwarstournament.helper.ResponseResult
import javax.inject.Inject

class MatchesRepository @Inject constructor(
    private val context: Context,
    private val gson: Gson
) : Repository<MatchesDetailData, String> {


    override suspend fun getData(requestParams: String): ResponseResult<MatchesDetailData> {
        val jsonString = context.getJsonFromAssets(requestParams)
        return try {
            ResponseResult.Success(gson.fromJson(jsonString, MatchesDetailData::class.java))
        } catch (exception: Exception) {
            ResponseResult.Error("Failed to parse jsonData", exception)
        }
    }
}