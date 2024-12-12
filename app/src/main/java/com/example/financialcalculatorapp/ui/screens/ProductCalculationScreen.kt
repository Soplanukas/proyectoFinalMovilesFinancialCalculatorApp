package com.example.financialcalculatorapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.financialcalculatorapp.presentation.viewModels.ProductCalculationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCalculationScreen(
    navController: NavController,
    viewModel: ProductCalculationViewModel = remember { ProductCalculationViewModel() }
) {
    val calculationHistory by viewModel.calculationHistory.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cálculos de Producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
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
            var basePrice by remember { mutableStateOf("") }
            var cost by remember { mutableStateOf("") }

            // Campo de entrada para el precio base
            OutlinedTextField(
                value = basePrice,
                onValueChange = { basePrice = it },
                label = { Text("Precio Base") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de entrada para el costo
            OutlinedTextField(
                value = cost,
                onValueChange = { cost = it },
                label = { Text("Costo") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para calcular
            Button(
                onClick = {
                    viewModel.calculateProductMetrics(
                        basePrice.toDoubleOrNull() ?: 0.0,
                        cost.toDoubleOrNull() ?: 0.0
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
                            Text("Precio con IVA: ${String.format("%.2f", calculation.priceWithIVA)}")
                            Text("Margen de Ganancia: ${String.format("%.2f%%", calculation.profitMargin)}")
                            Text("Punto de Equilibrio: ${String.format("%.2f", calculation.breakEvenPoint)}")
                            Text("ROI: ${String.format("%.2f%%", calculation.roi)}")
                        }
                    }
                }
            }
        }
    }
}