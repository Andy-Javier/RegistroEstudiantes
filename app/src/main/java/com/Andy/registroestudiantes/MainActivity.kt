package com.Andy.registroestudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Andy.registroestudiantes.presentation.EstudianteAddScreen
import com.Andy.registroestudiantes.presentation.EstudianteListScreen
import com.Andy.registroestudiantes.presentation.EstudianteViewModel
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
                val viewModel: EstudianteViewModel = hiltViewModel()

                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        EstudianteListScreen(viewModel) {
                            navController.navigate("add")
                        }
                    }
                    composable("add") {
                        EstudianteAddScreen(viewModel) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}