package com.example.financialcalculatorapp.presentation.viewModels

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financialcalculatorapp.domain.models.CalculationCategory
import com.example.financialcalculatorapp.domain.services.CalculationService
import com.example.financialcalculatorapp.presentation.viewModels.EmployerCalculationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmployeeCalculationViewModel(
    private val calculationService: CalculationService = CalculationService()
) : ViewModel() {
    private val _calculationHistory = MutableStateFlow<List<CalculationCategory.EmployeeCalculation>>(emptyList())
    val calculationHistory = _calculationHistory.asStateFlow()

    fun calculateEmployeeSalary(baseSalary: Double, overtimeHours: Double, isNightShift: Boolean, isSundayHoliday: Boolean) {
        viewModelScope.launch {
            val newCalculation = calculationService.calculateEmployeeSalary(baseSalary, overtimeHours, isNightShift, isSundayHoliday)
            _calculationHistory.value = _calculationHistory.value + newCalculation
        }
    }
}