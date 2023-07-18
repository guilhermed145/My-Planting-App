package com.myportfolio.myplantingapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Data model for a plant
 */
data class Plant (
    @StringRes val plantName: Int,
    @DrawableRes val imageResourceId: Int,
    @StringRes val canGrowBeside: Int,
    @StringRes val avoidGrowingCloseTo: Int,
    val soilTemperature: Pair<Int, Int>,
    val spaceBetweenPlants: Pair<Int, Int>,
    val harvestTime: Pair<Int, Int>
)