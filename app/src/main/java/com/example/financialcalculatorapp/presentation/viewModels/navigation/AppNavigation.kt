package com.example.financialcalculatorapp.presentation.viewModels.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financialcalculatorapp.ui.screens.*

@Composable
fun FinancialCalculatorApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { MainScreen(navController) }
        composable("product_calc") { ProductCalculationScreen(navController) }
        composable("employer_calc") { EmployerCalculationScreen(navController) }
        composable("employee_calc") { EmployeeCalculationScreen(navController) }
    }
}