package com.Andy.registroestudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.Andy.registroestudiantes.presentation.navigation.RegistroNavHost
import com.Andy.registroestudiantes.ui.theme.RegistroEstudiantesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroEstudiantesTheme {
                val navController = rememberNavController()
                RegistroNavHost(navController)
            }
        }
    }
}