package com.myportfolio.myplantingapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Data model for a plant
 */
data class Plant (
    @StringRes val plantName : Int,
    @DrawableRes val imageResourceId : Int,
)