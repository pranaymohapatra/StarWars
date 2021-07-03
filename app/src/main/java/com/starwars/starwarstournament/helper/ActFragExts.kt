package com.starwars.starwarstournament.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

inline fun <reified T : ViewModel> ViewModelStoreOwner.getViewModel(viewmodelFactory: ViewModelProvider.Factory): T {
    return ViewModelProvider(this, viewmodelFactory).get(T::class.java)
}