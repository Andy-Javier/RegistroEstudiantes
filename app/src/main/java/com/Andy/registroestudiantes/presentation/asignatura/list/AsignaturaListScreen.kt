package com.Andy.registroestudiantes.presentation.asignatura.list

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.Andy.registroestudiantes.domain.model.Asignatura
import com.Andy.registroestudiantes.ui.theme.RegistroEstudiantesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturaListScreen(
    viewModel: AsignaturaListViewModel = hiltViewModel(),
    onAddAsignatura: () -> Unit,
    onEditAsignatura: (Int) -> Unit,
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    AsignaturaListContent(
        uiState = uiState,
        onEvent = { event ->
            viewModel.onEvent(event)
        },
        onAddAsignatura = onAddAsignatura,
        onEditAsignatura = onEditAsignatura,
        onDrawer = onDrawer
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturaListContent(
    uiState: AsignaturaListState,
    onEvent: (AsignaturaListEvent) -> Unit,
    onAddAsignatura: () -> Unit = {},
    onEditAsignatura: (Int) -> Unit = {},
    onDrawer: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Asignaturas") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddAsignatura) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(uiState.asignaturas) { asignatura ->
                ListItem(
                    headlineContent = { Text(asignatura.nombre) },
                    supportingContent = { Text("Código: ${asignatura.codigo} - Aula: ${asignatura.aula} - Créditos: ${asignatura.creditos}") },
                    trailingContent = {
                        Row {
                            IconButton(onClick = { onEditAsignatura(asignatura.id ?: 0) }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
                            }
                            IconButton(onClick = { onEvent(AsignaturaListEvent.DeleteAsignatura(asignatura)) }) {
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
fun AsignaturaListPreview() {
    RegistroEstudiantesTheme {
        AsignaturaListContent(
            uiState = AsignaturaListState(
                asignaturas = listOf(
                    Asignatura(1, "ADM-101", "Administración", "A-101", 3),
                    Asignatura(2, "PRG-202", "Programación Aplicada 2", "B-205", 4)
                )
            ),
            onEvent = {}
        )
    }
}
