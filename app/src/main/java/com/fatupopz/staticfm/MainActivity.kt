package com.fatupopz.staticfm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.fatupopz.staticfm.ui.screens.HomeScreen
import com.fatupopz.staticfm.ui.screens.TopTracksScreen
import com.fatupopz.staticfm.ui.theme.StaticFMTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StaticFMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(
                        onNavigate = {},
                        onWeeklyRecapClick = {}
                    )
                }
            }
        }
    }
}