package com.example.financialcalculatorapp.presentation.viewModels.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculadora Financiera") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CalculationCategoryButton("Cálculos de Producto", "product_calc", navController)
            Spacer(modifier = Modifier.height(16.dp))
            CalculationCategoryButton("Cálculos de Empleador", "employer_calc", navController)
            Spacer(modifier = Modifier.height(16.dp))
            CalculationCategoryButton("Cálculos de Empleado", "employee_calc", navController)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CalculationCategoryButton(
    text: String,
    route: String,
    navController: NavController
) {
    Button(
        onClick = { navController.navigate(route) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text)
    }
}