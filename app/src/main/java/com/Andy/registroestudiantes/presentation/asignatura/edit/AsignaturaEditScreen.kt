package com.Andy.registroestudiantes.presentation.asignatura.edit

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
fun AsignaturaEditScreen(
    viewModel: AsignaturaEditViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    AsignaturaEditContent(
        uiState = uiState,
        onEvent = { event ->
            if (event is AsignaturaEditEvent.SaveAsignatura) {
                viewModel.onEvent(AsignaturaEditEvent.SaveAsignatura(onSuccess = onBack))
            } else {
                viewModel.onEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturaEditContent(
    uiState: AsignaturaEditState,
    onEvent: (AsignaturaEditEvent) -> Unit
) {
    Scaffold(topBar = { TopAppBar(title = { Text(if (uiState.asignaturaId == null) "Nueva Asignatura" else "Editar Asignatura") }) }) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {
            OutlinedTextField(
                value = uiState.codigo,
                onValueChange = { onEvent(AsignaturaEditEvent.CodigoChanged(it)) },
                label = { Text("Código") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { onEvent(AsignaturaEditEvent.NombreChanged(it)) },
                label = { Text("Nombre de la Asignatura") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.aula,
                onValueChange = { onEvent(AsignaturaEditEvent.AulaChanged(it)) },
                label = { Text("Aula") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.creditos,
                onValueChange = { onEvent(AsignaturaEditEvent.CreditosChanged(it)) },
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
                onClick = { onEvent(AsignaturaEditEvent.SaveAsignatura({})) },
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
fun AsignaturaEditPreview() {
    RegistroEstudiantesTheme {
        AsignaturaEditContent(
            uiState = AsignaturaEditState(
                codigo = "PRG-202",
                nombre = "Programación Aplicada 2",
                aula = "B-205",
                creditos = "4"
            ),
            onEvent = {}
        )
    }
}