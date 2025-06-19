package com.myportfolio.myplantingapp.data

import com.myportfolio.myplantingapp.R
import com.myportfolio.myplantingapp.model.Plant

/**
 * (PT-BR)
 * Singleton que fornece dados de todas as plantas usadas no aplicativo.
 *
 * (EN)
 * Singleton that provides data for all the plants used in the app.
 */
object PlantsDataProvider {
    val plantList: List<Plant> = listOf(
        Plant(
            R.string.beetroot,
            R.drawable.beetroot,
            R.string.beetroot_grow_beside,
            R.string.beetroot_avoid_growing,
            Pair(7, 25),
            Pair(20, 30),
            Pair(7, 10)
        ),
        Plant(
            R.string.broccoli,
            R.drawable.broccoli,
            R.string.broccoli_grow_beside,
            R.string.broccoli_avoid_growing,
            Pair(7, 30),
            Pair(35, 50),
            Pair(10, 16)
        ),
        Plant(
            R.string.cabbage,
            R.drawable.cabbage,
            R.string.cabbage_grow_beside,
            R.string.cabbage_avoid_growing,
            Pair(5, 18),
            Pair(50, 75),
            Pair(11, 15)
        ),
        Plant(
            R.string.chilli_pepper,
            R.drawable.chilli_pepper,
            R.string.chilli_pepper_grow_beside,
            R.string.chilli_pepper_avoid_growing,
            Pair(18, 35),
            Pair(40, 50),
            Pair(8, 11)
        ),
        Plant(
            R.string.eggplant,
            R.drawable.eggplant,
            R.string.eggplant_grow_beside,
            R.string.eggplant_avoid_growing,
            Pair(24, 32),
            Pair(60, 75),
            Pair(12, 15)
        ),
        Plant(
            R.string.leek,
            R.drawable.leek,
            R.string.leek_grow_beside,
            R.string.leek_avoid_growing,
            Pair(8, 30),
            Pair(10, 20),
            Pair(15, 18)
        ),
        Plant(
            R.string.lettuce,
            R.drawable.lettuce,
            R.string.lettuce_grow_beside,
            R.string.lettuce_avoid_growing,
            Pair(8, 27),
            Pair(20, 30),
            Pair(8, 12)
        ),
        Plant(
            R.string.onion,
            R.drawable.onion,
            R.string.onion_grow_beside,
            R.string.onion_avoid_growing,
            Pair(8, 30),
            Pair(5, 10),
            Pair(25, 34)
        ),
        Plant(
            R.string.parsnip,
            R.drawable.parsnip,
            R.string.parsnip_grow_beside,
            R.string.parsnip_avoid_growing,
            Pair(6, 21),
            Pair(8, 10),
            Pair(17, 20)
        ),
        Plant(
            R.string.peas,
            R.drawable.peas,
            R.string.peas_grow_beside,
            R.string.peas_avoid_growing,
            Pair(8, 24),
            Pair(5, 8),
            Pair(9, 12)
        ),
        Plant(
            R.string.potato,
            R.drawable.potato,
            R.string.potato_grow_beside,
            R.string.potato_avoid_growing,
            Pair(10, 30),
            Pair(30, 40),
            Pair(15, 20)
        ),
        Plant(
            R.string.radish,
            R.drawable.radish,
            R.string.radish_grow_beside,
            R.string.radish_avoid_growing,
            Pair(8, 30),
            Pair(3, 5),
            Pair(5, 7)
        ),
        Plant(
            R.string.spring_onion,
            R.drawable.spring_onion,
            R.string.spring_onion_grow_beside,
            R.string.spring_onion_avoid_growing,
            Pair(10, 20),
            Pair(1, 3),
            Pair(8, 12)
        ),
        Plant(
            R.string.turnip,
            R.drawable.turnip,
            R.string.turnip_grow_beside,
            R.string.turnip_avoid_growing,
            Pair(12, 30),
            Pair(12, 20),
            Pair(6, 9)
        ),
        Plant(
            R.string.watermelon,
            R.drawable.watermelon,
            R.string.watermelon_grow_beside,
            R.string.watermelon_avoid_growing,
            Pair(21, 35),
            Pair(60, 75),
            Pair(12, 17)
        )
    )
}