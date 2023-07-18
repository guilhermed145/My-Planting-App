package com.myportfolio.myplantingapp.ui

import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageAndVideo.toString
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.myportfolio.myplantingapp.R
import com.myportfolio.myplantingapp.model.Plant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPlantingApp() {
    val viewModel: AppViewModel = AppViewModel()
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
    ) {
        Crossfade(
            targetState = uiState.isInMainScreen,
            modifier = Modifier.padding(it)
        ) {
            if (it) {
                MainScreen(
                    onPlantItemClick = {
                        viewModel.navigateToPlantInfoScreen()
                    },
                    modifier = Modifier
                )
            } else {
                PlantInfoScreen(
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
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
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
    onPlantItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (modifier = modifier) {
        AppSearchBar()
        PlantsGrid(
            onItemClick = onPlantItemClick
        )
    }
}

@Composable
fun PlantsGrid(
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        items(30) {item ->
            PlantItem(onItemClick)
        }
        /**
         *  items(photos) { photo ->
         *      PhotoItem(photo)
         *  }
         */
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantItem(
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    plant: Plant = Plant(R.string.app_name,R.drawable.ic_launcher_background),
) {
    Card(
        elevation = CardDefaults.cardElevation(),
        onClick = onItemClick,
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
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = modifier
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = modifier.height(4.dp))
            Text(text = "Plant's Name")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    modifier : Modifier = Modifier
){
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Box (
        modifier = modifier
            .zIndex(1f)
            .fillMaxWidth()
    ){
        SearchBar(
            query = text,
            onQueryChange = { text = it },
            onSearch = { active = false },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = { Text("Hinted search text") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
            modifier = modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        ) {
            LazyColumn(
                modifier = modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(4) { idx ->
                    val resultText = "Suggestion $idx"
                    ListItem(
                        headlineContent = { Text(resultText) },
                        supportingContent = { Text("Additional info") },
                        leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                        modifier = modifier.clickable {
                            text = resultText
                            active = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PlantInfoScreen(
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
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(280.dp)
                    .fillMaxWidth()
                    .padding(20.dp)
            )
        }
        item {
            Text(
                text = "Plant's Name",
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
            PlantInfoRow()
            PlantInfoRow()
            PlantInfoRow()
            PlantInfoRow()
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
        //verticalAlignment = Alignment.Top,
        modifier = modifier.padding(vertical = 12.dp)
    ) {
        Text(
            text = infoTitle,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.weight(1f)
        )
        Text(infoValue, modifier = Modifier.weight(1f))
    }
}