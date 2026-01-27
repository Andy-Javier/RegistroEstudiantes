package com.Andy.registroestudiantes.presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.Andy.registroestudiantes.presentation.EstudianteAddScreen
import com.Andy.registroestudiantes.presentation.EstudianteListScreen
import com.Andy.registroestudiantes.presentation.EstudianteViewModel
import com.Andy.registroestudiantes.presentation.asignatura.AsignaturaAddScreen
import com.Andy.registroestudiantes.presentation.asignatura.AsignaturaListScreen
import com.Andy.registroestudiantes.presentation.asignatura.AsignaturaViewModel
import kotlinx.coroutines.launch

@Composable
fun RegistroNavHost(
    navHostController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    
    val estudianteViewModel: EstudianteViewModel = hiltViewModel()
    val asignaturaViewModel: AsignaturaViewModel = hiltViewModel()

    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.EstudianteList
        ) {
            composable<Screen.EstudianteList> {
                EstudianteListScreen(
                    viewModel = estudianteViewModel,
                    onAddEstudiante = {
                        navHostController.navigate(Screen.EstudianteAdd)
                    },
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

            composable<Screen.EstudianteAdd> {
                EstudianteAddScreen(
                    viewModel = estudianteViewModel,
                    onBack = {
                        navHostController.navigateUp()
                    }
                )
            }

            composable<Screen.AsignaturaList> {
                AsignaturaListScreen(
                    viewModel = asignaturaViewModel,
                    onAddAsignatura = {
                        navHostController.navigate(Screen.AsignaturaAdd)
                    },
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

            composable<Screen.AsignaturaAdd> {
                AsignaturaAddScreen(
                    viewModel = asignaturaViewModel,
                    onBack = {
                        navHostController.navigateUp()
                    }
                )
            }
        }
    }
}