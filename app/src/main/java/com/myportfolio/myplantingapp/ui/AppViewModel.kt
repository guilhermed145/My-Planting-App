package com.myportfolio.myplantingapp.ui

import android.content.Context
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
            },
            //searchBarHistory = listOf()
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

    fun updateSearchBarText(text : String) {
        _uiState.update {
            it.copy(searchBarText = text)
        }
    }

    fun updateSearchBarState(value : Boolean) {
        _uiState.update {
            it.copy(isSearchBarActive = value)
        }
    }

    fun addToSearchBarHistory(searchText : String) {
        if (searchText != "") {
            val newHistory: MutableList<String> = uiState.value.searchBarHistory
            newHistory.add(searchText)
            _uiState.update {
                it.copy(searchBarHistory = newHistory)
            }
        }
    }

    fun findSearchResults(context: Context) {
        val newSearchResultList: MutableList<Plant> = mutableListOf()
        plantList.forEach {
            if (
                context.resources.getString(it.plantName)
                    .contains(uiState.value.searchBarText, ignoreCase = true)
            ) {
                newSearchResultList.add(it)
            }
        }
        _uiState.update {
            it.copy(searchResultList = newSearchResultList)
        }
    }

}