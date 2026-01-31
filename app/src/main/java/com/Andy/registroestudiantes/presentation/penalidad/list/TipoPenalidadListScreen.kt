package com.Andy.registroestudiantes.presentation.penalidad.list

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
import com.Andy.registroestudiantes.domain.model.TipoPenalidad
import com.Andy.registroestudiantes.ui.theme.RegistroEstudiantesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipoPenalidadListScreen(
    viewModel: TipoPenalidadListViewModel = hiltViewModel(),
    onAddPenalidad: () -> Unit,
    onEditPenalidad: (Int) -> Unit,
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    TipoPenalidadListContent(
        uiState = uiState,
        onEvent = { event ->
            viewModel.onEvent(event)
        },
        onAddPenalidad = onAddPenalidad,
        onEditPenalidad = onEditPenalidad,
        onDrawer = onDrawer
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipoPenalidadListContent(
    uiState: TipoPenalidadListState,
    onEvent: (TipoPenalidadListEvent) -> Unit,
    onAddPenalidad: () -> Unit = {},
    onEditPenalidad: (Int) -> Unit = {},
    onDrawer: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Penalidades") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPenalidad) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(uiState.penalidades) { penalidad ->
                ListItem(
                    headlineContent = { Text(penalidad.nombre) },
                    supportingContent = { Text("${penalidad.descripcion} - Puntos: ${penalidad.puntosDescuento}") },
                    trailingContent = {
                        Row {
                            IconButton(onClick = { onEditPenalidad(penalidad.id ?: 0) }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
                            }
                            IconButton(onClick = { onEvent(TipoPenalidadListEvent.DeletePenalidad(penalidad)) }) {
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
fun TipoPenalidadListPreview() {
    RegistroEstudiantesTheme {
        TipoPenalidadListContent(
            uiState = TipoPenalidadListState(
                penalidades = listOf(
                    TipoPenalidad(1, "Tardanza", "Llegar tarde", 5),
                    TipoPenalidad(2, "Inasistencia", "Faltar a clase", 10)
                )
            ),
            onEvent = {}
        )
    }
}