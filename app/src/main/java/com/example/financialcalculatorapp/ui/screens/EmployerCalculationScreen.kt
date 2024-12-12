package com.example.financialcalculatorapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financialcalculatorapp.presentation.viewModels.EmployerCalculationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployerCalculationScreen(
    navController: NavController,
    viewModel: EmployerCalculationViewModel = viewModel()
) {
    val calculationHistory by viewModel.calculationHistory.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cálculos de Empleador") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var baseSalary by remember { mutableStateOf("") }

            // Campo de entrada para el salario base
            OutlinedTextField(
                value = baseSalary,
                onValueChange = { baseSalary = it },
                label = { Text("Salario Base") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para calcular
            Button(
                onClick = {
                    viewModel.calculateEmployerCosts(
                        baseSalary.toDoubleOrNull() ?: 0.0
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar el historial de cálculos
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(calculationHistory) { calculation ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Aportes Parafiscales: ${String.format("%.2f", calculation.parafiscalContributions)}")
                            Text("Seguridad Social: ${String.format("%.2f", calculation.socialSecurity)}")
                            Text("Prestaciones Sociales: ${String.format("%.2f", calculation.socialBenefits)}")
                            Text("Costo Total de Nómina: ${String.format("%.2f", calculation.totalPayrollCost)}")
                        }
                    }
                }
            }
        }
    }
}



