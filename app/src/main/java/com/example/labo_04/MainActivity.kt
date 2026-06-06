package com.example.labo_04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.labo_04.ui.navigation.AppNavigation
import com.example.labo_04.ui.screens.LoginScreen
import com.example.labo_04.ui.theme.AppTheme
import com.example.labo_04.ui.viewmodel.GpsViewModel
import com.example.labo_04.ui.viewmodel.SessionViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Los objetos de infraestructura ya viven en DemoDataApp
        val app = application as DemoData

        // 2. Instanciación de los ViewModels inyectando sus dependencias desde DemoDataApp
        val gpsViewModel     = GpsViewModel(app.gpsRepository)
        val sessionViewModel = SessionViewModel(app.sessionManager)

        setContent {
            // 3. Recolección reactiva de la preferencia del Modo Oscuro de DataStore
            val isLoggedIn by sessionViewModel.isLoggedIn.collectAsStateWithLifecycle()
            val isDarkModePref by sessionViewModel.isDarkMode.collectAsStateWithLifecycle()
            val usarModoOscuro = isDarkModePref ?: isSystemInDarkTheme()


            // 4. Inyección del estado dinámico al tema de Material Design 3
            AppTheme(darkTheme = usarModoOscuro, dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color    = MaterialTheme.colorScheme.background
                ) {
                    if (isLoggedIn) {
                        AppNavigation(
                            gpsViewModel = gpsViewModel,
                            sessionViewModel = sessionViewModel
                        )
                    } else {
                        LoginScreen { username, password, onResult ->

                            if (username == "jkn" && password == "jkn") {

                                lifecycleScope.launch {
                                    app.sessionManager.login(username)
                                    onResult(true)
                                }

                            } else {
                                onResult(false)
                            }
                        }
                    }
                }
            }
        }
    }
}