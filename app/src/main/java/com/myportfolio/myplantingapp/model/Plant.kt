package com.myportfolio.myplantingapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Data model for a plant
 */
data class Plant(
    @StringRes val plantName: Int = 0,
    @DrawableRes val imageResourceId: Int = 0,
    @StringRes val canGrowBeside: Int = 0,
    @StringRes val avoidGrowingCloseTo: Int = 0,
    val soilTemperature: Pair<Int, Int> = Pair(0, 0),
    val spaceBetweenPlants: Pair<Int, Int> = Pair(0, 0),
    val harvestTime: Pair<Int, Int> = Pair(0, 0)
)
