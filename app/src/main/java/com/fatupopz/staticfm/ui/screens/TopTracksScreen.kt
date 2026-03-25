package com.fatupopz.staticfm.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fatupopz.staticfm.R
import com.fatupopz.staticfm.ui.components.BottomNav
import com.fatupopz.staticfm.ui.components.NavScreen
import com.fatupopz.staticfm.ui.theme.*

data class TrackItem(
    val rank: String,
    val name: String,
    val artist: String,
    val plays: String,
    val duration: String,
    val playPercent: Float,
    val isHot: Boolean = false,
    val artColor1: Color,
    val artColor2: Color
)

// Placeholder tracks — luego vendrán de la Spotify API
val sampleTracks = listOf(
    TrackItem("01","Madrugada","Farmacos","47x","3:42",1.0f,true,Color(0xFF3C0A8C),Color(0xFF0A37B4)),
    TrackItem("02","Oceanica","Nina Tormenta","38x","4:15",0.81f,false,Color(0xFF0A1978),Color(0xFF0A5FAF)),
    TrackItem("03","Verano Peligroso","Cultivo","31x","3:28",0.66f,false,Color(0xFF0A5A37),Color(0xFF0A825A)),
    TrackItem("04","Ceniza","La Vida Boheme","24x","5:02",0.51f,false,Color(0xFF641E0A),Color(0xFFA0500A)),
    TrackItem("05","Bruma","Concepcion Quezada","19x","3:55",0.40f,false,Color(0xFF0A3C64),Color(0xFF0A648C)),
    TrackItem("06","Ciudad Noche","Ximena Sarinana","15x","4:20",0.32f,false,Color(0xFF500A50),Color(0xFF78148C)),
    TrackItem("07","Nada Nos Separa","Carla Morrison","12x","3:38",0.26f,false,Color(0xFF0A5050),Color(0xFF0A7878)),
    TrackItem("08","Mar de Flores","La Llorona","10x","4:48",0.21f,false,Color(0xFF32500A),Color(0xFF50820A)),
)

@Composable
fun TopTracksScreen(
    onBack: () -> Unit,
    onNavigate: (NavScreen) -> Unit
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
        Column(modifier = Modifier.fillMaxSize()) {

            // Header
            TopTracksHeader(onBack = onBack)

            // Time tabs
            TimeTabs(
                tabs = tabs,
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            // Summary bar
            SummaryBar()

            // Track list
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                itemsIndexed(sampleTracks) { _, track ->
                    TrackRow(track = track)
                }
            }

            // Bottom nav
            BottomNav(
                currentScreen = NavScreen.TRACKS,
                onNavigate = onNavigate
            )
        }
    }
}

@Composable
private fun TopTracksHeader(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 50.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "<",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = CyanLight
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.top_tracks_title),
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = CyanLight
        )
        Spacer(modifier = Modifier.weight(1f))
        // Sort button
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Sort ↓", fontSize = 12.sp, color = TextDim)
        }
    }
}

@Composable
private fun TimeTabs(
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
                            Brush.verticalGradient(colors = listOf(
                                Color(0xFF00B4DC).copy(alpha = 0.45f),
                                Color(0xFF008CBE).copy(alpha = 0.25f)
                            ))
                        else
                            Brush.verticalGradient(colors = listOf(
                                Color.White.copy(alpha = 0.07f),
                                Color.White.copy(alpha = 0.02f)
                            ))
                    )
                    .then(
                        if (isActive) Modifier else Modifier
                    )
                    .padding(horizontal = 14.dp)
                    .wrapContentWidth(),
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
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun SummaryBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(44.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF50B4FF).copy(alpha = 0.15f),
                        Color(0xFF1464C8).copy(alpha = 0.07f)
                    )
                )
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "247 tracks",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = CyanLight
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "·", fontSize = 12.sp, color = TextDim.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.this_month),
                fontSize = 12.sp,
                color = TextDim
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun TrackRow(track: TrackItem) {
    val isFirst = track.rank == "01"
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF64B4FF).copy(alpha = if (isFirst) 0.18f else 0.12f),
                        Color(0xFF2878C8).copy(alpha = 0.06f)
                    )
                )
            )
    ) {
        // Shine
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = if (isFirst) 0.12f else 0.07f),
                            Color.Transparent
                        )
                    )
                )
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rank
            Text(
                text = track.rank,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (isFirst) CyanLight else TextDim,
                modifier = Modifier.width(28.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            // Album art placeholder
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(track.artColor1, track.artColor2)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Shine
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .align(Alignment.TopCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.3f),
                                    Color.Transparent
                                )
                            )
                        )
                )
                // Aquí irá AsyncImage con Coil cuando tengamos la API
                Text(
                    text = track.name.first().toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Track info
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = track.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    if (track.isHot) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .height(18.dp)
                                .clip(RoundedCornerShape(9.dp))
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFFFF6432).copy(alpha = 0.8f),
                                            Color(0xFFFF3C14).copy(alpha = 0.6f)
                                        )
                                    )
                                )
                                .padding(horizontal = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "HOT",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }
                Text(
                    text = track.artist,
                    fontSize = 11.sp,
                    color = TextDim
                )
            }

            // Plays + duration + bar
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.width(56.dp)
            ) {
                Text(
                    text = track.plays,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isFirst) CyanLight else TextDim
                )
                Text(
                    text = track.duration,
                    fontSize = 10.sp,
                    color = TextDim.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Play bar
                Box(
                    modifier = Modifier
                        .width(44.dp)
                        .height(3.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFF003264).copy(alpha = 0.6f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(track.playPercent)
                            .clip(RoundedCornerShape(2.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF0A84FF),
                                        Color(0xFF00D2F0)
                                    )
                                )
                            )
                    )
                }
            }
        }
    }
}