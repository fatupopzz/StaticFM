package com.fatupopz.staticfm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fatupopz.staticfm.R
import com.fatupopz.staticfm.ui.theme.Cyan
import com.fatupopz.staticfm.ui.theme.CyanLight
import com.fatupopz.staticfm.ui.theme.TextDim
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

enum class NavScreen {
    HOME, TRACKS, ARTISTS, QUEMADO
}

@Composable
fun BottomNav(
    currentScreen: NavScreen,
    onNavigate: (NavScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF145090).copy(alpha = 0.65f),
                        Color(0xFF052864).copy(alpha = 0.9f)
                    )
                )
            )
    ) {
        // Top border glow
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xFF00C8E6).copy(alpha = 0.7f),
                            Color.Transparent
                        )
                    )
                )
        )
        // Top shine
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    )
                )
        )

        Row(modifier = Modifier.fillMaxSize()) {
            NavItem(
                labelRes = R.string.nav_home,
                iconRes = R.drawable.home,
                isActive = currentScreen == NavScreen.HOME,
                onClick = { onNavigate(NavScreen.HOME) },
                modifier = Modifier.weight(1f)
            )
            NavItem(
                labelRes = R.string.nav_tracks,
                iconRes = R.drawable.tracks,
                isActive = currentScreen == NavScreen.TRACKS,
                onClick = { onNavigate(NavScreen.TRACKS) },
                modifier = Modifier.weight(1f)
            )
            NavItem(
                labelRes = R.string.nav_artists,
                iconRes = R.drawable.icon_artists,
                isActive = currentScreen == NavScreen.ARTISTS,
                onClick = { onNavigate(NavScreen.ARTISTS) },
                modifier = Modifier.weight(1f)
            )
            NavItem(
                labelRes = R.string.nav_quemado,
                iconRes = R.drawable.quemados,
                isActive = currentScreen == NavScreen.QUEMADO,
                onClick = { onNavigate(NavScreen.QUEMADO) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun NavItem(
    labelRes: Int,
    iconRes: Int,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(70.dp),
            alpha = if (isActive) 1f else 0.45f
        )
    }
}