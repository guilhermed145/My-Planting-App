package com.myportfolio.myplantingapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.myportfolio.myplantingapp.R
import com.myportfolio.myplantingapp.data.PlantsDataProvider.plantList
import com.myportfolio.myplantingapp.model.Plant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * (PT-BR)
 * Esta é a classe da ViewModel. Ela gerencia os dados exibidos pelos composables e mantém uma
 * instância do estado de UI do aplicativo.
 * A ViewModel sobrevive a mudanças de configuração, como rotações de tela, garantindo que os dados
 * sejam preservados ao longo do ciclo de vida dos componentes de UI.
 *
 * (EN)
 * This is the ViewModel class. It manages the data displayed by the composables and holds an
 * instance of the app's UI state.
 * The ViewModel survives configuration changes like screen rotations, ensuring that the data is
 * preserved across the lifecycle of the UI components.
 */
class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        AppUiState(
            currentPlant = plantList.getOrElse(0) {
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
    val uiState: StateFlow<AppUiState> = _uiState

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

    fun updateSelectedPlant(selectedPlant: Plant) {
        _uiState.update {
            it.copy(currentPlant = selectedPlant)
        }
    }

    fun updateSearchBarText(text: String) {
        _uiState.update {
            it.copy(searchBarText = text)
        }
    }

    fun updateSearchBarState(value: Boolean) {
        _uiState.update {
            it.copy(isSearchBarActive = value)
        }
    }

    fun addToSearchBarHistory(searchText: String) {
        val searchHistory = uiState.value.searchBarHistory
        if (searchText == "" || searchText == searchHistory.lastOrNull()) {
            return
        }
        val newHistory: MutableList<String> = mutableListOf()
        if (searchHistory.isNotEmpty()) {
            newHistory.addAll(searchHistory)
            if (searchHistory.contains(searchText)) {
                newHistory.remove(searchText)
            }
        }
        newHistory.add(searchText)

        _uiState.update {
            it.copy(searchBarHistory = newHistory)
        }
    }

    fun findSearchResults(searchText: String, context: Context) {
        val newSearchResultList: MutableList<Plant> = mutableListOf()
        plantList.forEach {
            if (
                context.resources.getString(it.plantName)
                    .contains(searchText, ignoreCase = true)
            ) {
                newSearchResultList.add(it)
            }
        }
        _uiState.update {
            it.copy(searchResultList = newSearchResultList)
        }
    }

}