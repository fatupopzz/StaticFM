package com.fatupopz.staticfm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fatupopz.staticfm.data.api.SpotifyAuth
import com.fatupopz.staticfm.data.api.SpotifyTokenManager
import com.fatupopz.staticfm.data.api.SpotifyTokenService
import com.fatupopz.staticfm.navigation.NavGraph
import com.fatupopz.staticfm.navigation.Routes
import com.fatupopz.staticfm.ui.theme.StaticFMTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var tokenManager: SpotifyTokenManager
    private val tokenService = SpotifyTokenService.create()
    private var navController: NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tokenManager = SpotifyTokenManager(this)

        setContent {
            StaticFMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val controller = rememberNavController()
                    navController = controller
                    NavGraph(navController = controller)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { uri ->
            if (uri.scheme == "com.fatupopz.staticfm" && uri.host == "callback") {
                val code  = uri.getQueryParameter("code")
                val error = uri.getQueryParameter("error")

                if (code != null) {
                    exchangeCodeForToken(code)
                }
                if (error != null) {
                    android.util.Log.e("SpotifyAuth", "Error: $error")
                }
            }
        }
    }

    private fun exchangeCodeForToken(code: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val authHeader = SpotifyTokenService.basicAuthHeader(
                    clientId     = BuildConfig.SPOTIFY_CLIENT_ID,
                    clientSecret = BuildConfig.SPOTIFY_CLIENT_SECRET
                )
                val response = tokenService.getToken(
                    authorization = authHeader,
                    grantType     = "authorization_code",
                    code          = code,
                    redirectUri   = SpotifyAuth.REDIRECT_URI
                )
                tokenManager.saveTokens(
                    accessToken  = response.access_token,
                    refreshToken = response.refresh_token ?: "",
                    expiresIn    = response.expires_in
                )

                // Navegar a Home en el main thread
                CoroutineScope(Dispatchers.Main).launch {
                    navController?.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }

                android.util.Log.d("SpotifyAuth", "✅ Token guardado, navegando a Home")

            } catch (e: Exception) {
                android.util.Log.e("SpotifyAuth", "Error: ${e.message}")
            }
        }
    }
}