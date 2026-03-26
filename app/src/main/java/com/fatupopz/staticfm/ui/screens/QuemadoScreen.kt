package com.fatupopz.staticfm.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.fatupopz.staticfm.ui.components.BottomNav
import com.fatupopz.staticfm.ui.components.NavScreen

@Composable
fun QuemadoScreen(
    onBack: () -> Unit,
    onNavigate: (NavScreen) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF020C23), Color(0xFF002D5A))
                )
            )
    ) {
        Text(
            text = "Quemado — coming soon",
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
        BottomNav(
            currentScreen = NavScreen.QUEMADO,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}