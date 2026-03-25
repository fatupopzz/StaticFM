package com.fatupopz.staticfm.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.fatupopz.staticfm.ui.components.BottomNav
import com.fatupopz.staticfm.ui.components.NavScreen
import com.fatupopz.staticfm.ui.theme.*

@Composable
fun HomeScreen(
    onNavigate: (NavScreen) -> Unit,
    onWeeklyRecapClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf(
        stringResource(R.string.tab_this_week),
        stringResource(R.string.tab_4_weeks),
        stringResource(R.string.tab_6_months),
        stringResource(R.string.tab_all_time)
    )

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
        // Background orbs
        BackgroundOrbs()

        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    // Status bar space
                    Spacer(modifier = Modifier.height(48.dp))

                    // Header
                    HomeHeader()

                    Spacer(modifier = Modifier.height(8.dp))

                    // Time tabs
                    HomeTimeTabs(
                        tabs = tabs,
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Stat cards
                    StatCards()

                    Spacer(modifier = Modifier.height(12.dp))

                    // Now Playing
                    NowPlayingCard()

                    Spacer(modifier = Modifier.height(12.dp))

                    // Weekly Recap card
                    WeeklyRecapCard(onClick = onWeeklyRecapClick)

                    Spacer(modifier = Modifier.height(12.dp))

                    // Top Tracks mini
                    SectionLabel(text = "TOP TRACKS")

                    Spacer(modifier = Modifier.height(8.dp))
                }

                itemsIndexed(sampleTracks.take(4)) { _, track ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        TrackRow(track = track)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Bottom nav
            BottomNav(
                currentScreen = NavScreen.HOME,
                onNavigate = onNavigate
            )
        }
    }
}

@Composable
private fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF0A84FF), Color(0xFF00D4FF))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.35f),
                                Color.Transparent
                            )
                        )
                    )
            )
            Text(
                text = "F",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = "StaticFM",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = CyanLight
            )
            Text(
                text = "fatupopz",
                fontSize = 11.sp,
                color = TextDim
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Settings button
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "⚙", fontSize = 18.sp, color = TextDim)
        }
    }
}

@Composable
private fun HomeTimeTabs(
    tabs: List<String>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        itemsIndexed(tabs) { index, tab ->
            val isActive = index == selectedTab
            Box(
                modifier = Modifier
                    .height(28.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        if (isActive)
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF00B4DC).copy(alpha = 0.45f),
                                    Color(0xFF008CBE).copy(alpha = 0.25f)
                                )
                            )
                        else
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.07f),
                                    Color.White.copy(alpha = 0.02f)
                                )
                            )
                    )
                    .clickable { onTabSelected(index) }
                    .padding(horizontal = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tab,
                    fontSize = 10.sp,
                    fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (isActive) CyanLight else TextDim
                )
            }
        }
    }
}

@Composable
private fun StatCards() {
    val stats = listOf(
        Triple("TRACKS", "847", stringResource(R.string.this_month)),
        Triple("MINUTES", "3.2K", "~54 hours"),
        Triple("ARTISTS", "63", "12 new"),
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        stats.forEach { (label, value, sub) ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(78.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF50B4FF).copy(alpha = 0.22f),
                                Color(0xFF1464C8).copy(alpha = 0.1f)
                            )
                        )
                    )
            ) {
                // Shine
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .align(Alignment.TopCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.16f),
                                    Color.Transparent
                                )
                            )
                        )
                )
                Column(
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                ) {
                    Text(text = label, fontSize = 8.sp, color = TextDim, letterSpacing = 1.5.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = CyanLight)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = sub, fontSize = 9.sp, color = TextDim)
                }
            }
        }
    }
}

@Composable
private fun NowPlayingCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(78.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF64C8FF).copy(alpha = 0.25f),
                        Color(0xFF1E78C8).copy(alpha = 0.12f)
                    )
                )
            )
    ) {
        // Shine
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.White.copy(alpha = 0.18f), Color.Transparent)
                    )
                )
        )

        // Contenido principal
        Column(modifier = Modifier.fillMaxSize()) {

            // Fila de info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Album art
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF3C0A8C), Color(0xFF0A32B4))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                            .align(Alignment.TopCenter)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.White.copy(alpha = 0.3f), Color.Transparent)
                                )
                            )
                    )
                    Text(text = "♪", fontSize = 22.sp, color = CyanLight)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(SpotifyGreen)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = stringResource(R.string.playing_now),
                            fontSize = 8.sp,
                            color = SpotifyGreen,
                            letterSpacing = 2.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Madrugada",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = "Farmacos",
                        fontSize = 11.sp,
                        color = TextDim
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Barra de progreso
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 70.dp, end = 12.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFF00508C).copy(alpha = 0.5f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.62f)
                            .clip(RoundedCornerShape(2.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF00B4DC), Color(0xFF50F0C8))
                                )
                            )
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "2:14",
                    fontSize = 9.sp,
                    color = TextDim.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
private fun WeeklyRecapCard(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        SectionLabel(text = stringResource(R.string.this_week_section))
        Spacer(modifier = Modifier.height(8.dp))
    }

    Spacer(modifier = Modifier.height(24.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(90.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF00A0C8).copy(alpha = 0.35f),
                        Color(0xFF0064A0).copy(alpha = 0.15f)
                    )
                )
            )
            .clickable { onClick() }
    ) {
        // Shine
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.White.copy(alpha = 0.18f), Color.Transparent)
                    )
                )
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Calendar icon
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Cyan.copy(alpha = 0.5f),
                                Cyan.copy(alpha = 0.3f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .align(Alignment.TopCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.White.copy(alpha = 0.25f), Color.Transparent)
                            )
                        )
                )
                Text(text = "▦", fontSize = 24.sp, color = CyanLight)
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Mini bar chart decorative
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.width(70.dp).height(32.dp)
            ) {
                listOf(30, 50, 38, 65, 55, 80, 45).forEach { h ->
                    val barH = (h * 32f / 100f).dp
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(barH)
                            .padding(horizontal = 1.dp)
                            .clip(RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp))
                            .background(
                                if (h == 80) Cyan.copy(alpha = 0.9f)
                                else Cyan.copy(alpha = 0.4f)
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.weekly_recap),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // NEW badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9.dp))
                            .background(SpotifyGreen.copy(alpha = 0.9f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "NEW",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                    }
                }
                Text(
                    text = "Mar 17 - 24  →",
                    fontSize = 10.sp,
                    color = Cyan.copy(alpha = 0.85f)
                )
            }

            Text(text = ">", fontSize = 18.sp, color = TextDim.copy(alpha = 0.4f))
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 9.sp,
        color = TextDim,
        letterSpacing = 2.5.sp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}