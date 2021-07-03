package com.starwars.starwarstournament.helper

data class GlobalState(val state: State, val message: String? = null)
enum class State {
    ERROR,
    SUCCESS,
    LOADING
}