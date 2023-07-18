package com.myportfolio.myplantingapp.ui

import androidx.lifecycle.ViewModel
import com.myportfolio.myplantingapp.R
import com.myportfolio.myplantingapp.data.PlantsDataProvider.plantList
import com.myportfolio.myplantingapp.model.Plant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        AppUiState(
            plantList.getOrElse(0) {
                Plant(
                    R.string.beetroot,
                    R.drawable.ic_launcher_background,
                    R.string.beetroot_grow_beside,
                    R.string.beetroot_avoid_growing,
                    Pair(7, 25),
                    Pair(20, 30),
                    Pair(7, 10)
                )
            }
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

    fun updateSelectedPlant(selectedPlant : Plant){
        _uiState.update {
            it.copy(currentPlant = selectedPlant)
        }
    }

}