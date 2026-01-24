package com.Andy.registroestudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Andy.registroestudiantes.presentation.EstudianteAddScreen
import com.Andy.registroestudiantes.presentation.EstudianteListScreen
import com.Andy.registroestudiantes.presentation.EstudianteViewModel
import com.Andy.registroestudiantes.presentation.asignatura.AsignaturaAddScreen
import com.Andy.registroestudiantes.presentation.asignatura.AsignaturaListScreen
import com.Andy.registroestudiantes.presentation.asignatura.AsignaturaViewModel
import com.Andy.registroestudiantes.presentation.navigation.DrawerMenu
import com.Andy.registroestudiantes.presentation.navigation.Screen
import com.Andy.registroestudiantes.ui.theme.RegistroEstudiantesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroEstudiantesTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                // Declaramos los ViewModels aquí para compartirlos entre las pantallas de cada módulo
                val estudianteViewModel: EstudianteViewModel = hiltViewModel()
                val asignaturaViewModel: AsignaturaViewModel = hiltViewModel()

                DrawerMenu(
                    drawerState = drawerState,
                    navHostController = navController
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.EstudianteList
                    ) {
                        // Módulo Estudiantes
                        composable<Screen.EstudianteList> {
                            EstudianteListScreen(
                                viewModel = estudianteViewModel,
                                onAddEstudiante = { navController.navigate(Screen.EstudianteAdd) },
                                onDrawer = { scope.launch { drawerState.open() } }
                            )
                        }
                        composable<Screen.EstudianteAdd> {
                            EstudianteAddScreen(viewModel = estudianteViewModel) {
                                navController.popBackStack()
                            }
                        }

                        // Módulo Asignaturas
                        composable<Screen.AsignaturaList> {
                            AsignaturaListScreen(
                                viewModel = asignaturaViewModel,
                                onAddAsignatura = { navController.navigate(Screen.AsignaturaAdd) },
                                onDrawer = { scope.launch { drawerState.open() } }
                            )
                        }
                        composable<Screen.AsignaturaAdd> {
                            AsignaturaAddScreen(viewModel = asignaturaViewModel) {
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }
}