package com.Andy.registroestudiantes.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.Andy.registroestudiantes.data.local.EstudianteEntity
import com.Andy.registroestudiantes.ui.theme.RegistroEstudiantesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstudianteListScreen(viewModel: EstudianteViewModel, onAddEstudiante: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    
    EstudianteListContent(
        uiState = uiState,
        onIntent = { intent -> 
            if (intent is EstudianteIntent.NuevoEstudiante || intent is EstudianteIntent.OnEstudianteClick) {
                viewModel.onIntent(intent)
                onAddEstudiante()
            } else {
                viewModel.onIntent(intent)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstudianteListContent(
    uiState: EstudianteUIState,
    onIntent: (EstudianteIntent) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Lista de Estudiantes") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onIntent(EstudianteIntent.NuevoEstudiante)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(uiState.estudiantes) { estudiante ->
                ListItem(
                    modifier = Modifier.clickable {
                        onIntent(EstudianteIntent.OnEstudianteClick(estudiante))
                    },
                    headlineContent = { Text(estudiante.nombres) },
                    supportingContent = { Text("${estudiante.email} - ${estudiante.edad} años") },
                    trailingContent = {
                        IconButton(onClick = { onIntent(EstudianteIntent.DeleteEstudiante(estudiante)) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
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
fun EstudianteAddScreen(viewModel: EstudianteViewModel, onBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    
    EstudianteAddContent(
        uiState = uiState,
        onIntent = { intent ->
            if (intent is EstudianteIntent.SaveEstudiante) {
                viewModel.onIntent(EstudianteIntent.SaveEstudiante(onSuccess = onBack))
            } else {
                viewModel.onIntent(intent)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstudianteAddContent(
    uiState: EstudianteUIState,
    onIntent: (EstudianteIntent) -> Unit
) {
    Scaffold(topBar = { TopAppBar(title = { Text(if (uiState.estudianteId == null) "Nuevo Estudiante" else "Editar Estudiante") }) }) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {
            OutlinedTextField(
                value = uiState.nombres,
                onValueChange = { onIntent(EstudianteIntent.NombreChanged(it)) },
                label = { Text("Nombres") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { onIntent(EstudianteIntent.EmailChanged(it)) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.edad,
                onValueChange = { onIntent(EstudianteIntent.EdadChanged(it)) },
                label = { Text("Edad") },
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
                onClick = { onIntent(EstudianteIntent.SaveEstudiante({})) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(if (uiState.estudianteId == null) "Guardar" else "Actualizar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EstudianteListPreview() {
    RegistroEstudiantesTheme {
        EstudianteListContent(
            uiState = EstudianteUIState(
                estudiantes = listOf(
                    EstudianteEntity(1, "Andy Javier", "Andyjavier@gmail.com", 21),
                    EstudianteEntity(2, "Ashley", "Ashley@gmail.com", 22)
                )
            ),
            onIntent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EstudianteAddPreview() {
    RegistroEstudiantesTheme {
        EstudianteAddContent(
            uiState = EstudianteUIState(
                nombres = "Andy Javier",
                email = "Andyjavier@gmail.com",
                edad = "21"
            ),
            onIntent = {}
        )
    }
}