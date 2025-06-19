package com.myportfolio.myplantingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.myportfolio.myplantingapp.ui.theme.MyPlantingAppTheme
import com.myportfolio.myplantingapp.ui.MyPlantingApp

/**
 * (PT-BR)
 * Esta é a activity principal do aplicativo.
 * Quando criada, verifica qual o tamanho da tela do dispositivo e altera o tamanho dos componentes
 * de UI dependendo da largura da tela.
 *
 * (EN)
 * This is the app's main Activity.
 * When created, it checks the device's screen size and adjusts the size of the UI components
 * depending on the screen width.
 */
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPlantingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val windowSize = calculateWindowSizeClass(activity = this)
                    MyPlantingApp(windowSize = windowSize.widthSizeClass)
                }
            }
        }
    }
}

/**
 * (PT-BR)
 * Este Composable mostra uma pré-visualização da UI do aplicativo sendo executado em uma tela de
 * tamanho pequeno.
 *
 * (EN)
 * This Composable shows a preview of the app's UI running on a small-sized screen.
 */
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    MyPlantingAppTheme {
        MyPlantingApp()
    }
}

/**
 * (PT-BR)
 * Este Composable mostra uma pré-visualização da UI do aplicativo sendo executado em uma tela de
 * tamanho médio.
 *
 * (EN)
 * This Composable shows a preview of the app's UI running on a medium-sized screen.
 */
@Preview(showBackground = true, widthDp = 700)
@Composable
fun AppPreviewMediumSize() {
    MyPlantingAppTheme {
        MyPlantingApp(WindowWidthSizeClass.Medium)
    }
}

/**
 * (PT-BR)
 * Este Composable mostra uma pré-visualização da UI do aplicativo sendo executado em uma tela de
 * tamanho grande.
 *
 * (EN)
 * This Composable shows a preview of the app's UI running on a big-sized screen.
 */
@Preview(showBackground = true, widthDp = 1000)
@Composable
fun AppPreviewExpandedSize() {
    MyPlantingAppTheme {
        MyPlantingApp(WindowWidthSizeClass.Expanded)
    }
}