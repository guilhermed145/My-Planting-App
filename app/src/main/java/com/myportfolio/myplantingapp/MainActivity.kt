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
 * The activity for the app
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
 * Main app preview
 */
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    MyPlantingAppTheme {
        MyPlantingApp()
    }
}

/**
 * App preview for medium screens
 */
@Preview(showBackground = true, widthDp = 700)
@Composable
fun AppPreviewMediumSize() {
    MyPlantingAppTheme {
        MyPlantingApp(WindowWidthSizeClass.Medium)
    }
}

/**
 * App preview for large screens
 */
@Preview(showBackground = true, widthDp = 1000)
@Composable
fun AppPreviewExpandedSize() {
    MyPlantingAppTheme {
        MyPlantingApp(WindowWidthSizeClass.Expanded)
    }
}