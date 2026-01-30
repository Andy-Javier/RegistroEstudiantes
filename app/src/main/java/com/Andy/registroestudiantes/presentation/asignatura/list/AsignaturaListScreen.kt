package com.Andy.registroestudiantes.presentation.asignatura.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        // LOGICA PARA LISTA VACÍA
        if (uiState.asignaturas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(32.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No hay asignaturas",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Aún no has registrado ninguna asignatura. Pulsa el botón '+' para comenzar.",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(uiState.asignaturas) { asignatura ->
                    ListItem(
                        headlineContent = { Text(asignatura.nombre) },
                        supportingContent = {
                            Text("Código: ${asignatura.codigo} - Aula: ${asignatura.aula} - Créditos: ${asignatura.creditos}")
                        },
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

@Preview(showBackground = true, name = "Estado Vacío")
@Composable
fun AsignaturaListEmptyPreview() {
    RegistroEstudiantesTheme {
        AsignaturaListContent(
            uiState = AsignaturaListState(asignaturas = emptyList()),
            onEvent = {}
        )
    }
}