package com.starwars.starwarstournament.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.starwars.starwarstournament.common.dto.pointstable.PointsItem
import com.starwars.starwarstournament.domain.GetPointsUseCase
import javax.inject.Inject

class PointsTableViewModel @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase,
) : BaseViewModel() {
    val pointsTableData: LiveData<List<PointsItem>>
        get() = _pointsTableData

    private val _pointsTableData = MutableLiveData<List<PointsItem>>()

    fun getPointsDetailData() {
        startBackgroundJob({
            getPointsUseCase.getPointsTableSorted()
        }, {
            _pointsTableData.value = it.sortedDescending()
        })
    }

}