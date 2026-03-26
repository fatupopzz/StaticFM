package com.fatupopz.staticfm.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.fatupopz.staticfm.R
import com.fatupopz.staticfm.data.model.TrackItem
import com.fatupopz.staticfm.ui.components.BottomNav
import com.fatupopz.staticfm.ui.components.NavScreen
import com.fatupopz.staticfm.ui.theme.*
import com.fatupopz.staticfm.viewmodel.TracksViewModel

@Composable
fun TopTracksScreen(
    onBack: () -> Unit,
    onNavigate: (NavScreen) -> Unit,
    viewModel: TracksViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(1) } // default: medium_term
    val timeRanges = listOf("short_term", "medium_term", "long_term")
    val tabs = listOf(
        stringResource(R.string.tab_this_week),
        stringResource(R.string.tab_4_weeks),
        stringResource(R.string.tab_6_months),
    )

    LaunchedEffect(selectedTab) {
        viewModel.loadTracks(timeRanges[selectedTab])
    }

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

            TopTracksHeader(onBack = onBack)

            TimeTabs(
                tabs = tabs,
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            SummaryBar(count = uiState.tracks.size)

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = CyanLight)
                    }
                }
                uiState.error != null -> {
                    Box(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: ${uiState.error}",
                            color = TextDim,
                            fontSize = 12.sp
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(9.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        itemsIndexed(uiState.tracks) { index, track ->
                            RealTrackRow(rank = index + 1, track = track)
                        }
                    }
                }
            }

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
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun SummaryBar(count: Int) {
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
                text = "$count tracks",
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
fun RealTrackRow(rank: Int, track: TrackItem) {
    val isFirst = rank == 1
    val rankStr = rank.toString().padStart(2, '0')

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
                text = rankStr,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (isFirst) CyanLight else TextDim,
                modifier = Modifier.width(28.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            // Album art real con Coil
            AsyncImage(
                model = track.albumArtUrl,
                contentDescription = track.name,
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Track info
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = track.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        maxLines = 1
                    )
                    if (isFirst) {
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
                    text = track.artistNames,
                    fontSize = 11.sp,
                    color = TextDim,
                    maxLines = 1
                )
            }

            // Duration + popularity bar
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.width(56.dp)
            ) {
                Text(
                    text = track.durationFormatted,
                    fontSize = 10.sp,
                    color = TextDim.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(4.dp))
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
                            .fillMaxWidth(track.popularity / 100f)
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