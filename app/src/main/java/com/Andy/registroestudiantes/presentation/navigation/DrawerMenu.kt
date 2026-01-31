package com.Andy.registroestudiantes.presentation.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.Andy.registroestudiantes.R
import com.Andy.registroestudiantes.ui.theme.RegistroEstudiantesTheme
import kotlinx.coroutines.launch

@Composable
fun DrawerMenu(
    drawerState: DrawerState,
    navHostController: NavHostController,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val selectedItem = remember { mutableStateOf("Estudiantes") }

    fun handleItemClick(destination: Screen, title: String) {
        navHostController.navigate(destination) {
            launchSingleTop = true
        }
        selectedItem.value = title
        scope.launch { drawerState.close() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerSheetContent(
                selectedItem = selectedItem.value,
                onItemClick = { title, screen ->
                    handleItemClick(screen, title)
                }
            )
        }
    ) {
        content()
    }
}

@Composable
fun DrawerSheetContent(
    selectedItem: String,
    onItemClick: (String, Screen) -> Unit
) {
    ModalDrawerSheet(modifier = Modifier.width(280.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Registro App",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            modifier = Modifier.padding(16.dp)
        )
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            item {
                DrawerItem(
                    title = stringResource(R.string.drawer_estudiantes),
                    icon = Icons.Default.Person,
                    isSelected = selectedItem == stringResource(R.string.drawer_estudiantes)
                ) {
                    onItemClick(it, Screen.EstudianteList)
                }

                DrawerItem(
                    title = stringResource(R.string.drawer_asignaturas),
                    icon = Icons.Default.List,
                    isSelected = selectedItem == stringResource(R.string.drawer_asignaturas)
                ) {
                    onItemClick(it, Screen.AsignaturaList)
                }

                DrawerItem(
                    title = stringResource(R.string.drawer_penalidades),
                    icon = Icons.Default.Warning,
                    isSelected = selectedItem == stringResource(R.string.drawer_penalidades)
                ) {
                    onItemClick(it, Screen.TipoPenalidadList)
                }
            }
        }
    }
}

@Composable
fun DrawerItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: (String) -> Unit
) {
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected) Color.Black else Color.Gray
            )
        },
        label = { Text(text = title) },
        selected = isSelected,
        onClick = { onClick(title) },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

@Preview(showBackground = true)
@Composable
fun DrawerMenuPreview() {
    RegistroEstudiantesTheme {
        DrawerSheetContent(
            selectedItem = "Estudiantes",
            onItemClick = { _, _ -> }
        )
    }
}