package com.myportfolio.myplantingapp.ui

import androidx.lifecycle.ViewModel
import com.myportfolio.myplantingapp.data.PlantsDataProvider.plantsList
import com.myportfolio.myplantingapp.model.Plant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        AppUiState(
            plantsList = plantsList
        )
    )
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

}

data class AppUiState(
    val plantsList: List<Plant> = emptyList()
)