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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstudianteListScreen(viewModel: EstudianteViewModel, onAddEstudiante: () -> Unit) {
    val estudiantes = viewModel.estudiantes.collectAsState().value
    Scaffold(
        topBar = { TopAppBar(title = { Text("Lista de Estudiantes") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.nuevoEstudiante()
                onAddEstudiante()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(estudiantes) { estudiante ->
                ListItem(
                    modifier = Modifier.clickable {
                        viewModel.onEstudianteClick(estudiante)
                        onAddEstudiante()
                    },
                    headlineContent = { Text(estudiante.nombres) },
                    supportingContent = { Text("${estudiante.email} - ${estudiante.edad} años") },
                    trailingContent = {
                        IconButton(onClick = { viewModel.deleteEstudiante(estudiante) }) {
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
    Scaffold(topBar = { TopAppBar(title = { Text(if (viewModel.estudianteId == null) "Nuevo Estudiante" else "Editar Estudiante") }) }) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {
            OutlinedTextField(
                value = viewModel.nombres,
                onValueChange = { viewModel.nombres = it },
                label = { Text("Nombres") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.edad,
                onValueChange = { viewModel.edad = it },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            viewModel.errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Button(
                onClick = { viewModel.saveEstudiante { onBack() } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(if (viewModel.estudianteId == null) "Guardar" else "Actualizar")
            }
        }
    }
}