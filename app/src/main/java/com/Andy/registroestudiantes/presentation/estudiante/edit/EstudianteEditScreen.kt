package com.Andy.registroestudiantes.presentation.estudiante.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.Andy.registroestudiantes.ui.theme.RegistroEstudiantesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstudianteEditScreen(
    viewModel: EstudianteEditViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    EstudianteEditContent(
        uiState = uiState,
        onEvent = { event ->
            if (event is EstudianteEditEvent.SaveEstudiante) {
                viewModel.onEvent(EstudianteEditEvent.SaveEstudiante(onSuccess = onBack))
            } else {
                viewModel.onEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstudianteEditContent(
    uiState: EstudianteEditState,
    onEvent: (EstudianteEditEvent) -> Unit
) {
    Scaffold(topBar = { TopAppBar(title = { Text(if (uiState.estudianteId == null) "Nuevo Estudiante" else "Editar Estudiante") }) }) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {
            OutlinedTextField(
                value = uiState.nombres,
                onValueChange = { onEvent(EstudianteEditEvent.NombreChanged(it)) },
                label = { Text("Nombres") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { onEvent(EstudianteEditEvent.EmailChanged(it)) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.edad,
                onValueChange = { onEvent(EstudianteEditEvent.EdadChanged(it)) },
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
                onClick = { onEvent(EstudianteEditEvent.SaveEstudiante({})) },
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
fun EstudianteEditPreview() {
    RegistroEstudiantesTheme {
        EstudianteEditContent(
            uiState = EstudianteEditState(
                nombres = "Juan Perez",
                email = "juan@gmail.com",
                edad = "20"
            ),
            onEvent = {}
        )
    }
}