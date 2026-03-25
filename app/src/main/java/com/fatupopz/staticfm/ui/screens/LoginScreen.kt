package com.fatupopz.staticfm.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.fatupopz.staticfm.ui.theme.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale

data class FeatureItem(val icon: String, val labelRes: Int)
@Composable
fun LoginScreen(onLoginClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF020C23),
                        Color(0xFF031E46),
                        Color(0xFF002D5A)
                    )
                )
            )
    ) {
        // Orbs de fondo
        BackgroundOrbs()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // App Icon
            AppIcon()

            Spacer(modifier = Modifier.height(24.dp))

            // Título
            Text(
                text = "StaticFM",
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                color = CyanLight,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline bilingüe
            Text(
                text = stringResource(R.string.tagline),
                fontSize = 14.sp,
                color = TextDim,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Botón Spotify
            SpotifyLoginButton(onClick = onLoginClick)

            Spacer(modifier = Modifier.height(16.dp))

            // Privacy note
            Text(
                text = stringResource(R.string.privacy_note),
                fontSize = 11.sp,
                color = TextDim,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Feature cards
            Text(
                text = stringResource(R.string.what_youll_get),
                fontSize = 11.sp,
                color = TextDim,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            FeatureCards()

            Spacer(modifier = Modifier.height(24.dp))

            // Language pill
            LanguagePill()

            Spacer(modifier = Modifier.height(24.dp))

            // Terms
            Text(
                text = stringResource(R.string.terms),
                fontSize = 10.sp,
                color = TextDim.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                letterSpacing = 0.3.sp
            )
        }
    }
}

@Composable
private fun AppIcon() {
    Image(
        painter = painterResource(id = R.drawable.staticsymbol),
        contentDescription = "StaticFM",
        modifier = Modifier.size(100.dp)
    )
}

@Composable
private fun SpotifyLoginButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            painter = painterResource(id = R.drawable.spotify),
            contentDescription = stringResource(R.string.continue_with_spotify),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = stringResource(R.string.continue_with_spotify),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.padding(start = 58.dp)
        )
    }
}
@Composable
private fun FeatureCards() {
    val features = listOf(
        FeatureItem("◈", R.string.feature_weekly),
        FeatureItem("♛", R.string.feature_artists),
        FeatureItem("✦", R.string.feature_retrowrap),
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        features.forEach { item ->
            FeatureCard(
                icon = item.icon,
                label = stringResource(item.labelRes),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun FeatureCard(
    icon: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.height(90.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.features),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = icon, fontSize = 24.sp, color = CyanLight)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun LanguagePill() {
    val locale = java.util.Locale.getDefault().displayLanguage
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(SpotifyGreen)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Auto-detected: $locale",
                fontSize = 11.sp,
                color = TextDim
            )
        }
    }
}

@Composable
fun BackgroundOrbs() {
    // Orb top-left
    Box(
        modifier = Modifier
            .size(500.dp)
            .offset(x = (-120).dp, y = (-80).dp)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF0090DC).copy(alpha = 0.45f),
                        Color.Transparent
                    )
                )
            )
    )
    // Orb center-right
    Box(
        modifier = Modifier
            .size(380.dp)
            .offset(x = 180.dp, y = 480.dp)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF00C8B4).copy(alpha = 0.3f),
                        Color.Transparent
                    )
                )
            )
    )
}