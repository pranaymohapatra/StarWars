package com.starwars.starwarstournament.domain

import com.starwars.starwarstournament.helper.ResponseResult

interface Repository<T, Params> {
    suspend fun getData(requestParams: Params): ResponseResult<T>
}