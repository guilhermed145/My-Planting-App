package com.myportfolio.myplantingapp.ui

import com.myportfolio.myplantingapp.model.Plant

data class AppUiState (
    val plantsList: List<Plant> = emptyList(),
    val isInMainScreen: Boolean = true
)