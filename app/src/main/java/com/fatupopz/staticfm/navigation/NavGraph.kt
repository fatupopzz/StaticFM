package com.fatupopz.staticfm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fatupopz.staticfm.ui.components.NavScreen
import com.fatupopz.staticfm.ui.screens.*

object Routes {
    const val LOGIN        = "login"
    const val HOME         = "home"
    const val TOP_TRACKS   = "top_tracks"
    const val TOP_ARTISTS  = "top_artists"
    const val WEEKLY_RECAP = "weekly_recap"
    const val QUEMADO      = "quemado"
    const val RETROWRAP    = "retrowrap"
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.LOGIN
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                onNavigate = { screen ->
                    when (screen) {
                        NavScreen.HOME    -> {}
                        NavScreen.TRACKS  -> navController.navigate(Routes.TOP_TRACKS)
                        NavScreen.ARTISTS -> navController.navigate(Routes.TOP_ARTISTS)
                        NavScreen.QUEMADO -> navController.navigate(Routes.QUEMADO)
                    }
                },
                onWeeklyRecapClick = {
                    navController.navigate(Routes.WEEKLY_RECAP)
                }
            )
        }

        composable(Routes.TOP_TRACKS) {
            TopTracksScreen(
                onBack = { navController.popBackStack() },
                onNavigate = { screen ->
                    when (screen) {
                        NavScreen.HOME    -> navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = false }
                        }
                        NavScreen.TRACKS  -> {}
                        NavScreen.ARTISTS -> navController.navigate(Routes.TOP_ARTISTS)
                        NavScreen.QUEMADO -> navController.navigate(Routes.QUEMADO)
                    }
                }
            )
        }

        composable(Routes.TOP_ARTISTS) {
            TopArtistsScreen(
                onBack = { navController.popBackStack() },
                onNavigate = { screen ->
                    when (screen) {
                        NavScreen.HOME    -> navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = false }
                        }
                        NavScreen.TRACKS  -> navController.navigate(Routes.TOP_TRACKS)
                        NavScreen.ARTISTS -> {}
                        NavScreen.QUEMADO -> navController.navigate(Routes.QUEMADO)
                    }
                }
            )
        }

        composable(Routes.WEEKLY_RECAP) {
            WeeklyRecapScreen(
                onBack = { navController.popBackStack() },
                onNavigate = { screen ->
                    when (screen) {
                        NavScreen.HOME    -> navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = false }
                        }
                        NavScreen.TRACKS  -> navController.navigate(Routes.TOP_TRACKS)
                        NavScreen.ARTISTS -> navController.navigate(Routes.TOP_ARTISTS)
                        NavScreen.QUEMADO -> navController.navigate(Routes.QUEMADO)
                    }
                }
            )
        }

        composable(Routes.QUEMADO) {
            QuemadoScreen(
                onBack = { navController.popBackStack() },
                onNavigate = { screen ->
                    when (screen) {
                        NavScreen.HOME    -> navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = false }
                        }
                        NavScreen.TRACKS  -> navController.navigate(Routes.TOP_TRACKS)
                        NavScreen.ARTISTS -> navController.navigate(Routes.TOP_ARTISTS)
                        NavScreen.QUEMADO -> {}
                    }
                }
            )
        }

        composable(Routes.RETROWRAP) {
            RetroWrapScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}