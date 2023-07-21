package com.myportfolio.myplantingapp.ui

import com.myportfolio.myplantingapp.R
import com.myportfolio.myplantingapp.model.Plant

data class AppUiState(
    val currentPlant: Plant = Plant(
        R.string.beetroot,
        R.drawable.ic_launcher_background,
        R.string.beetroot_grow_beside,
        R.string.beetroot_avoid_growing,
        Pair(7, 25),
        Pair(20, 30),
        Pair(7, 10)
    ),
    val isInMainScreen: Boolean = true,
    val searchBarText: String = "",
    val isSearchBarActive: Boolean = false,
    val searchBarHistory: List<String> = listOf(),
    val searchResultList: List<Plant> = listOf(),
)