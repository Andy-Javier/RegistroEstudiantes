package com.Andy.registroestudiantes.presentation.asignatura

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.Andy.registroestudiantes.domain.model.Asignatura
import com.Andy.registroestudiantes.ui.theme.RegistroEstudiantesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturaListScreen(
    viewModel: AsignaturaViewModel,
    onAddAsignatura: () -> Unit,
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    AsignaturaListContent(
        uiState = uiState,
        onIntent = { intent -> 
            if (intent is AsignaturaIntent.NuevaAsignatura || intent is AsignaturaIntent.OnAsignaturaClick) {
                viewModel.onIntent(intent)
                onAddAsignatura()
            } else {
                viewModel.onIntent(intent)
            }
        },
        onDrawer = onDrawer
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturaListContent(
    uiState: AsignaturaUIState,
    onIntent: (AsignaturaIntent) -> Unit,
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
            FloatingActionButton(onClick = {
                onIntent(AsignaturaIntent.NuevaAsignatura)
            }) {
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
                            IconButton(onClick = { onIntent(AsignaturaIntent.OnAsignaturaClick(asignatura)) }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
                            }
                            IconButton(onClick = { onIntent(AsignaturaIntent.DeleteAsignatura(asignatura)) }) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturaAddScreen(viewModel: AsignaturaViewModel, onBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    
    AsignaturaAddContent(
        uiState = uiState,
        onIntent = { intent ->
            if (intent is AsignaturaIntent.SaveAsignatura) {
                viewModel.onIntent(AsignaturaIntent.SaveAsignatura(onSuccess = onBack))
            } else {
                viewModel.onIntent(intent)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturaAddContent(
    uiState: AsignaturaUIState,
    onIntent: (AsignaturaIntent) -> Unit
) {
    Scaffold(topBar = { TopAppBar(title = { Text(if (uiState.asignaturaId == null) "Nueva Asignatura" else "Editar Asignatura") }) }) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {
            OutlinedTextField(
                value = uiState.codigo,
                onValueChange = { onIntent(AsignaturaIntent.CodigoChanged(it)) },
                label = { Text("Código") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { onIntent(AsignaturaIntent.NombreChanged(it)) },
                label = { Text("Nombre de la Asignatura") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.aula,
                onValueChange = { onIntent(AsignaturaIntent.AulaChanged(it)) },
                label = { Text("Aula") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.creditos,
                onValueChange = { onIntent(AsignaturaIntent.CreditosChanged(it)) },
                label = { Text("Créditos") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            uiState.errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Button(
                onClick = { onIntent(AsignaturaIntent.SaveAsignatura({})) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(if (uiState.asignaturaId == null) "Guardar" else "Actualizar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AsignaturaListPreview() {
    RegistroEstudiantesTheme {
        AsignaturaListContent(
            uiState = AsignaturaUIState(
                asignaturas = listOf(
                    Asignatura(1, "ADM-101", "Administración", "A-101", 3),
                    Asignatura(2, "PRG-202", "Programación Aplicada 2", "B-205", 4)
                )
            ),
            onIntent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AsignaturaAddPreview() {
    RegistroEstudiantesTheme {
        AsignaturaAddContent(
            uiState = AsignaturaUIState(
                codigo = "PRG-202",
                nombre = "Programación Aplicada 2",
                aula = "B-205",
                creditos = "4"
            ),
            onIntent = {}
        )
    }
}