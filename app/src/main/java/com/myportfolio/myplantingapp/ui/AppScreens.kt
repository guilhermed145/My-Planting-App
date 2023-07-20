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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.myportfolio.myplantingapp.R
import com.myportfolio.myplantingapp.data.PlantsDataProvider.plantList
import com.myportfolio.myplantingapp.model.Plant

@Composable
fun MyPlantingApp(
    //windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact
) {
    val viewModel = AppViewModel()
    val uiState by viewModel.uiState.collectAsState()

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
        ) { isInMainScreen ->
            if (isInMainScreen) {
                MainScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onPlantItemClick = {
                        viewModel.updateSelectedPlant(it)
                        viewModel.navigateToPlantInfoScreen()
                    }
                )
            } else {
                PlantInfoScreen(
                    plant = uiState.currentPlant,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(
    onBackButtonClick: () -> Unit,
    isInMainScreen: Boolean,
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
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.LightGray,
            titleContentColor = Color.Gray
        )
    )
}

@Composable
fun MainScreen(
    viewModel : AppViewModel,
    uiState: AppUiState,
    onPlantItemClick: (Plant) -> Unit,
    modifier: Modifier = Modifier
) {
    Column (modifier = modifier) {
        AppSearchBar(
            viewModel = viewModel,
            uiState = uiState
        )
        PlantsGrid(
            uiState = uiState,
            onItemClick = onPlantItemClick
        )
    }
}

@Composable
fun PlantsGrid(
    uiState : AppUiState,
    onItemClick: (Plant) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        if (uiState.searchBarText != "") {
            if (uiState.searchResultList.isEmpty()) {
                item {
                    Text(
                        text = "No plant was found.",
                        modifier = modifier.padding(8.dp),
                    )
                }
            } else {
                items(uiState.searchResultList.size) {index ->
                    PlantItem(uiState.searchResultList[index], onItemClick)
                }
            }
        } else {
            items(plantList.size) {index ->
                PlantItem(plantList[index], onItemClick)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantItem(
    plant: Plant,
    onItemClick: (Plant) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(),
        onClick = {onItemClick(plant)},
        modifier = modifier.padding(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .padding(4.dp, 8.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(plant.imageResourceId),
                contentDescription = null,
                modifier = modifier
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = modifier.height(4.dp))
            Text(text = stringResource(plant.plantName))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    viewModel: AppViewModel,
    uiState: AppUiState,
    modifier : Modifier = Modifier
){

    val currentContext : Context = LocalContext.current

    Box (
        modifier = modifier
            .zIndex(1f)
            .fillMaxWidth()
    ){
        SearchBar(
            query = uiState.searchBarText,
            onQueryChange = { viewModel.updateSearchBarText(it) },
            onSearch = {
                viewModel.updateSearchBarState(false)
                viewModel.findSearchResults(currentContext)
                viewModel.addToSearchBarHistory(it)
            },
            active = uiState.isSearchBarActive,
            onActiveChange = {
                viewModel.updateSearchBarState(it)
            },
            placeholder = { Text("Which plant are you looking for?") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (uiState.isSearchBarActive) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            viewModel.updateSearchBarText("")
                            viewModel.updateSearchBarState(false)
                        }
                    )
                }
            },
            modifier = modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        ) {
            uiState.searchBarHistory.asReversed().forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(12.dp)
                ){
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

@Composable
fun PlantInfoScreen(
    plant : Plant,
    modifier: Modifier = Modifier
){
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
                    .height(280.dp)
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        item {
            Text(
                text = stringResource(plant.plantName),
                color = Color.DarkGray,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(12.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            PlantInfoRow(
                infoTitle = "Soil temperature",
                infoValue = plant.soilTemperature.first.toString() + "°C to "
                        + plant.soilTemperature.second + "°C"
            )
            PlantInfoRow(
                infoTitle = "Space between plants",
                infoValue = plant.spaceBetweenPlants.first.toString() + " to "
                        + plant.spaceBetweenPlants.second + "cm apart"
            )
            PlantInfoRow(
                infoTitle = "Harvest time",
                infoValue = plant.harvestTime.first.toString() + " to "
                        + plant.harvestTime.second + " weeks"
            )
            PlantInfoRow(
                infoTitle = "Can grow beside",
                infoValue = stringResource(plant.canGrowBeside)
            )
            PlantInfoRow(
                infoTitle = "Avoid growing close to",
                infoValue = stringResource(plant.avoidGrowingCloseTo)
            )
        }
    }
}

@Composable
fun PlantInfoRow(
    modifier: Modifier = Modifier,
    infoTitle : String = "Info Title",
    infoValue : String = "Info Value",
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
        Text(infoValue, modifier = Modifier
            .weight(1f)
            .fillMaxWidth())
    }
}