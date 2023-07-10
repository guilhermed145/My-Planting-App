package com.myportfolio.myplantingapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myportfolio.myplantingapp.R
import com.myportfolio.myplantingapp.model.Plant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPlantingApp(
    modifier : Modifier = Modifier
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {}
            )
        },
        modifier = modifier
    ) {
        PlantsGrid(modifier = modifier.padding(it))
    }
}

@Composable
fun PlantsGrid(
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(24.dp),
        modifier = modifier
    ) {
        /**
         *  items(photos) { photo ->
         *      PhotoItem(photo)
         *  }
         */
    }
}

@Composable
fun PlantItem(
    plant: Plant,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column() {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null
            )
            Text(text = "Plant's Name")
        }
    }
}
