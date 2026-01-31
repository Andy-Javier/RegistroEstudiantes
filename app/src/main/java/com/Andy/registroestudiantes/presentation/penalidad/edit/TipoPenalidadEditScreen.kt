package com.Andy.registroestudiantes.presentation.penalidad.edit

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
fun TipoPenalidadEditScreen(
    viewModel: TipoPenalidadEditViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    TipoPenalidadEditContent(
        uiState = uiState,
        onEvent = { event ->
            if (event is TipoPenalidadEditEvent.SavePenalidad) {
                viewModel.onEvent(TipoPenalidadEditEvent.SavePenalidad(onSuccess = onBack))
            } else {
                viewModel.onEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipoPenalidadEditContent(
    uiState: TipoPenalidadEditState,
    onEvent: (TipoPenalidadEditEvent) -> Unit
) {
    Scaffold(topBar = { TopAppBar(title = { Text(if (uiState.tipoId == null) "Nuevo Tipo de Penalidad" else "Editar Tipo de Penalidad") }) }) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { onEvent(TipoPenalidadEditEvent.NombreChanged(it)) },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.descripcion,
                onValueChange = { onEvent(TipoPenalidadEditEvent.DescripcionChanged(it)) },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.puntosDescuento,
                onValueChange = { onEvent(TipoPenalidadEditEvent.PuntosChanged(it)) },
                label = { Text("Puntos de Descuento") },
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
                onClick = { onEvent(TipoPenalidadEditEvent.SavePenalidad({})) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(if (uiState.tipoId == null) "Guardar" else "Actualizar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipoPenalidadEditPreview() {
    RegistroEstudiantesTheme {
        TipoPenalidadEditContent(
            uiState = TipoPenalidadEditState(
                nombre = "Tardanza",
                descripcion = "Llegar tarde a clase",
                puntosDescuento = "5"
            ),
            onEvent = {}
        )
    }
}