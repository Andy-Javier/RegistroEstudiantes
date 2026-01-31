package com.Andy.registroestudiantes.presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.Andy.registroestudiantes.presentation.estudiante.edit.EstudianteEditScreen
import com.Andy.registroestudiantes.presentation.estudiante.list.EstudianteListScreen
import com.Andy.registroestudiantes.presentation.asignatura.edit.AsignaturaEditScreen
import com.Andy.registroestudiantes.presentation.asignatura.list.AsignaturaListScreen
import com.Andy.registroestudiantes.presentation.penalidad.edit.TipoPenalidadEditScreen
import com.Andy.registroestudiantes.presentation.penalidad.list.TipoPenalidadListScreen
import kotlinx.coroutines.launch

@Composable
fun RegistroNavHost(
    navHostController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.EstudianteList
        ) {
            // Estudiantes
            composable<Screen.EstudianteList> {
                EstudianteListScreen(
                    onAddEstudiante = {
                        navHostController.navigate(Screen.Estudiante(0))
                    },
                    onEditEstudiante = { id ->
                        navHostController.navigate(Screen.Estudiante(id))
                    },
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

            composable<Screen.Estudiante> {
                EstudianteEditScreen(
                    onBack = {
                        navHostController.navigateUp()
                    }
                )
            }

            // Asignaturas
            composable<Screen.AsignaturaList> {
                AsignaturaListScreen(
                    onAddAsignatura = {
                        navHostController.navigate(Screen.Asignatura(0))
                    },
                    onEditAsignatura = { id ->
                        navHostController.navigate(Screen.Asignatura(id))
                    },
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

            composable<Screen.Asignatura> {
                AsignaturaEditScreen(
                    onBack = {
                        navHostController.navigateUp()
                    }
                )
            }

            // Tipos de Penalidades
            composable<Screen.TipoPenalidadList> {
                TipoPenalidadListScreen(
                    onAddPenalidad = {
                        navHostController.navigate(Screen.TipoPenalidad(0))
                    },
                    onEditPenalidad = { id ->
                        navHostController.navigate(Screen.TipoPenalidad(id))
                    },
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

            composable<Screen.TipoPenalidad> {
                TipoPenalidadEditScreen(
                    onBack = {
                        navHostController.navigateUp()
                    }
                )
            }
        }
    }
}