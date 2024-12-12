package com.example.financialcalculatorapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialcalculatorapp.domain.models.CalculationCategory
import com.example.financialcalculatorapp.domain.services.CalculationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmployerCalculationViewModel(
    private val calculationService: CalculationService = CalculationService()
) : ViewModel() {
    private val _calculationHistory = MutableStateFlow<List<CalculationCategory.EmployerCalculation>>(emptyList())
    val calculationHistory = _calculationHistory.asStateFlow()

    fun calculateEmployerCosts(baseSalary: Double) {
        viewModelScope.launch {
            val newCalculation = calculationService.calculateEmployerCosts(baseSalary)
            _calculationHistory.value = _calculationHistory.value + newCalculation // Agrega al historial
        }
    }
}