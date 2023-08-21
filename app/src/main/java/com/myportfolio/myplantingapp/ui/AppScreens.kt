package com.myportfolio.myplantingapp.ui

import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myportfolio.myplantingapp.R
import com.myportfolio.myplantingapp.data.PlantsDataProvider.plantList
import com.myportfolio.myplantingapp.model.Plant

/**
 * This is the mains app function.
 * It contains the view model, ui state and the current context.
 * Also has a Scaffold that contains the appbar and the two main screens.
 */
@Composable
fun MyPlantingApp(
    windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact
) {
    val viewModel: AppViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val currentContext: Context = LocalContext.current

    Scaffold(
        topBar = {
            MainAppBar(
                onBackButtonClick = {
                    viewModel.navigateToMainScreen()
                },
                isInMainScreen = uiState.isInMainScreen
            )
        }
    ) { paddingValues ->
        Crossfade(
            targetState = uiState.isInMainScreen,
            modifier = Modifier.padding(paddingValues)
        ) { targetState ->
            if (targetState) {
                MainScreen(
                    onSearchBarQueryChange = {
                        viewModel.updateSearchBarText(it)
                    },
                    onSearchBarSearch = {
                        viewModel.updateSearchBarState(false)
                        viewModel.findSearchResults(currentContext)
                        viewModel.addToSearchBarHistory(it)
                    },
                    onSearchBarActiveChange = {
                        viewModel.updateSearchBarState(it)
                    },
                    onSearchBarCloseIconClick = {
                        viewModel.updateSearchBarText("")
                        viewModel.updateSearchBarState(false)
                    },
                    onPlantItemClick = {
                        viewModel.updateSelectedPlant(it)
                        viewModel.navigateToPlantInfoScreen()
                    },
                    searchBarText = uiState.searchBarText,
                    searchResultList = uiState.searchResultList,
                    isSearchBarActive = uiState.isSearchBarActive,
                    searchBarHistory = uiState.searchBarHistory,
                    windowSize = windowSize
                )
            } else {
                PlantInfoScreen(
                    plant = uiState.currentPlant,
                    windowSize = windowSize
                )
            }
        }
    }
}

/**
 * This function represents the app's Appbar.
 * It shows the app's name and a navigation icon so the user can go back to the main screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(
    onBackButtonClick: () -> Unit,
    isInMainScreen: Boolean,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(stringResource(R.string.app_name))
        },
        navigationIcon = {
            if (!isInMainScreen) {
                IconButton(onClick = onBackButtonClick) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        },
        modifier = modifier
    )
}

/**
 * The main screen that contains the search bar and the plants grid.
 */
@Composable
fun MainScreen(
    onSearchBarQueryChange: (String) -> Unit,
    onSearchBarSearch: (String) -> Unit,
    onSearchBarActiveChange: (Boolean) -> Unit,
    onSearchBarCloseIconClick: () -> Unit,
    onPlantItemClick: (Plant) -> Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    searchBarText: String = "",
    searchResultList: List<Plant> = mutableListOf(),
    isSearchBarActive: Boolean = false,
    searchBarHistory: List<String> = mutableListOf(),
    ) {
    Column(modifier = modifier) {
        AppSearchBar(
            onQueryChange = onSearchBarQueryChange,
            onSearch = onSearchBarSearch,
            onActiveChange = onSearchBarActiveChange,
            onCloseIconClick = onSearchBarCloseIconClick,
            searchBarText = searchBarText,
            isSearchBarActive = isSearchBarActive,
            searchBarHistory = searchBarHistory
        )
        PlantsGrid(
            onItemClick = onPlantItemClick,
            searchBarText = searchBarText,
            searchResultList = searchResultList,
            windowSize = windowSize,
        )
    }
}

/**
 * A grid that shows a list of plants.
 * It shows all the plants by default, but can only show a few depending on the search bar result.
 * The card's size can change depending on the window size.
 */
@Composable
fun PlantsGrid(
    onItemClick: (Plant) -> Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    searchBarText: String = "",
    searchResultList: List<Plant> = mutableListOf()
) {
    LazyVerticalGrid(
        columns = when (windowSize) {
            WindowWidthSizeClass.Compact -> GridCells.Adaptive(120.dp)
            WindowWidthSizeClass.Expanded -> GridCells.Adaptive(200.dp)
            else -> GridCells.Adaptive(160.dp)
        },
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        if (searchBarText != "") {
            if (searchResultList.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.no_plant_was_found),
                        modifier = modifier.padding(8.dp),
                    )
                }
            } else {
                items(searchResultList.size) { index ->
                    PlantItem(searchResultList[index], onItemClick, windowSize)
                }
            }
        } else {
            items(plantList.size) { index ->
                PlantItem(plantList[index], onItemClick, windowSize)
            }
        }
    }
}

/**
 * The PlantItem function represents a card that contains the plant's name and picture.
 * Can be clicked to navigate to the plant's information screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantItem(
    plant: Plant,
    onItemClick: (Plant) -> Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = CardDefaults.cardElevation(),
        onClick = { onItemClick(plant) },
        modifier = modifier.padding(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(plant.imageResourceId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .clip(RoundedCornerShape(8.dp))
                    .then(
                        when (windowSize) {
                            WindowWidthSizeClass.Compact -> Modifier.height(100.dp)
                            WindowWidthSizeClass.Expanded -> Modifier.height(180.dp)
                            else -> Modifier.height(140.dp)
                        }
                    )
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = stringResource(plant.plantName),
                fontSize = 18.sp
            )
        }
    }
}

/**
 * This function represents the app's search bar.
 * The search bar can be used to look for a plant containing a specific name.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onCloseIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    searchBarText: String = "",
    isSearchBarActive: Boolean = false,
    searchBarHistory: List<String> = mutableListOf()
) {
    Box(
        modifier = modifier
            .zIndex(1f)
            .fillMaxWidth()
    ) {
        SearchBar(
            query = searchBarText,
            onQueryChange = { onQueryChange(it) },
            onSearch = {
                onSearch(it)
            },
            active = isSearchBarActive,
            onActiveChange = {
                onActiveChange(it)
            },
            placeholder = { Text("Which plant are you looking for?") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (searchBarText != "") {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.clickable(
                            role = Role.Button,
                            onClick = onCloseIconClick
                        )
                    )
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
        ) {
            searchBarHistory.asReversed().forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(text = it)
                }
            }
        }
    }
}

/**
 * This is the plant's information screen. It shows a column containing all the plant's information.
 * The plant's image size can change depending on the window size.
 */
@Composable
fun PlantInfoScreen(
    plant: Plant,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        item {
            Image(
                painter = painterResource(plant.imageResourceId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(20.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .then(
                        when (windowSize) {
                            WindowWidthSizeClass.Compact -> Modifier.height(280.dp)
                            WindowWidthSizeClass.Expanded -> Modifier.height(360.dp)
                            else -> Modifier.height(320.dp)
                        }
                    )
            )
        }
        item {
            Text(
                text = stringResource(plant.plantName),
                //color = Color.DarkGray,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(12.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
        item {
            PlantInfoRow(
                infoTitle = stringResource(R.string.soil_temperature),
                infoValue = plant.soilTemperature.first.toString() + "°C to "
                        + plant.soilTemperature.second + "°C"
            )
        }
        item {
            PlantInfoRow(
                infoTitle = stringResource(R.string.space_between_plants),
                infoValue = plant.spaceBetweenPlants.first.toString() + " to "
                        + plant.spaceBetweenPlants.second + "cm apart"
            )
        }
        item {
            PlantInfoRow(
                infoTitle = stringResource(R.string.harvest_time),
                infoValue = plant.harvestTime.first.toString() + " to "
                        + plant.harvestTime.second + " weeks"
            )
        }
        item {
            PlantInfoRow(
                infoTitle = stringResource(R.string.can_grow_beside),
                infoValue = stringResource(plant.canGrowBeside)
            )
        }
        item {
            PlantInfoRow(
                infoTitle = stringResource(R.string.avoid_growing_close_to),
                infoValue = stringResource(plant.avoidGrowingCloseTo)
            )
        }
    }
}

/**
 * A simple function representing a row that contains a plant's specific information.
 */
@Composable
fun PlantInfoRow(
    modifier: Modifier = Modifier,
    infoTitle: String = "Info Title",
    infoValue: String = "Info Value",
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = 20.dp)
    ) {
        Text(
            text = infoTitle,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = if (infoValue == "null") {
                ""
            } else {
                infoValue
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
    }
}