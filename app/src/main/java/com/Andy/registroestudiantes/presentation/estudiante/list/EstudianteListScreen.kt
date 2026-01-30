package com.Andy.registroestudiantes.presentation.estudiante.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.Andy.registroestudiantes.domain.model.Estudiante
import com.Andy.registroestudiantes.ui.theme.RegistroEstudiantesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstudianteListScreen(
    viewModel: EstudianteListViewModel = hiltViewModel(),
    onAddEstudiante: () -> Unit,
    onEditEstudiante: (Int) -> Unit,
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    EstudianteListContent(
        uiState = uiState,
        onEvent = { event ->
            viewModel.onEvent(event)
        },
        onAddEstudiante = onAddEstudiante,
        onEditEstudiante = onEditEstudiante,
        onDrawer = onDrawer
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstudianteListContent(
    uiState: EstudianteListState,
    onEvent: (EstudianteListEvent) -> Unit,
    onAddEstudiante: () -> Unit = {},
    onEditEstudiante: (Int) -> Unit = {},
    onDrawer: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Estudiantes") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddEstudiante) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(uiState.estudiantes) { estudiante ->
                ListItem(
                    headlineContent = { Text(estudiante.nombres) },
                    supportingContent = { Text("${estudiante.email} - ${estudiante.edad} años") },
                    trailingContent = {
                        Row {
                            IconButton(onClick = { onEditEstudiante(estudiante.id ?: 0) }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
                            }
                            IconButton(onClick = { onEvent(EstudianteListEvent.DeleteEstudiante(estudiante)) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                )
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EstudianteListPreview() {
    RegistroEstudiantesTheme {
        EstudianteListContent(
            uiState = EstudianteListState(
                estudiantes = listOf(
                    Estudiante(1, "Juan Perez", "juan@gmail.com", 20),
                    Estudiante(2, "Maria Lopez", "maria@gmail.com", 22)
                )
            ),
            onEvent = {}
        )
    }
}