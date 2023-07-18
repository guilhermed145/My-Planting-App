package com.myportfolio.myplantingapp.ui

import androidx.lifecycle.ViewModel
import com.myportfolio.myplantingapp.data.PlantsDataProvider.plantsList
import com.myportfolio.myplantingapp.model.Plant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        AppUiState(
            plantsList = plantsList,
        )
    )
    val uiState = _uiState.asStateFlow()

    fun navigateToMainScreen() {
        _uiState.update {
            it.copy(isInMainScreen = true)
        }
    }

    fun navigateToPlantInfoScreen() {
        _uiState.update {
            it.copy(isInMainScreen = false)
        }
    }

}