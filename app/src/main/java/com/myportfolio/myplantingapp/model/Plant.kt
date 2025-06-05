package com.myportfolio.myplantingapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * (PT-BR)
 * Esta classe representa um modelo de uma planta, contendo todos os dados necessários.
 *
 * (EN)
 * This class represents a plant model, holding all the basic data needed.
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
