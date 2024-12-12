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
import com.example.financialcalculatorapp.presentation.viewModels.EmployeeCalculationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeCalculationScreen(
    navController: NavController,
    viewModel: EmployeeCalculationViewModel = viewModel()
) {
    val calculationHistory by viewModel.calculationHistory.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cálculos de Empleado") },
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
            var overtimeHours by remember { mutableStateOf("") }
            var isNightShift by remember { mutableStateOf(false) }
            var isSundayHoliday by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = baseSalary,
                onValueChange = { baseSalary = it },
                label = { Text("Salario Base") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = overtimeHours,
                onValueChange = { overtimeHours = it },
                label = { Text("Horas Extra") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Casillas de verificación para horas nocturnas y dominicales/festivas
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Horas nocturnas")
                Checkbox(checked = isNightShift, onCheckedChange = { isNightShift = it })
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Horas dominicales/festivas")
                Checkbox(checked = isSundayHoliday, onCheckedChange = { isSundayHoliday = it })
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.calculateEmployeeSalary(
                        baseSalary.toDoubleOrNull() ?: 0.0,
                        overtimeHours.toDoubleOrNull() ?: 0.0,
                        isNightShift,
                        isSundayHoliday
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                            Text("Salario Neto: ${String.format("%.2f", calculation.netSalary)}")
                            Text("Deducciones: ${String.format("%.2f", calculation.payrollDeductions)}")
                            Text("Compensación Horas Extra Diurnas: ${String.format("%.2f", calculation.overtimeCompensation)}")
                            Text("Compensación Horas Extra Nocturnas: ${String.format("%.2f", calculation.overtimeNightCompensation)}")
                            Text("Compensación Horas Extra Dominicales/Festivas: ${String.format("%.2f", calculation.overtimeSundayHolidayCompensation)}")
                        }
                    }
                }
            }
        }
    }
}


