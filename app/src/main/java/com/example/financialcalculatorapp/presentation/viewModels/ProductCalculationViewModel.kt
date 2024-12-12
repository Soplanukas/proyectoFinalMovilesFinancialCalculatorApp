package com.example.financialcalculatorapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialcalculatorapp.domain.models.CalculationCategory
import com.example.financialcalculatorapp.domain.services.CalculationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductCalculationViewModel(
    private val calculationService: CalculationService = CalculationService()
) : ViewModel() {
    private val _calculationHistory = MutableStateFlow<List<CalculationCategory.ProductCalculation>>(emptyList())
    val calculationHistory = _calculationHistory.asStateFlow()

    fun calculateProductMetrics(basePrice: Double, cost: Double) {
        viewModelScope.launch {
            val newCalculation = calculationService.calculateProductMetrics(basePrice, cost)
            _calculationHistory.value = _calculationHistory.value + newCalculation // Agrega al historial
        }
    }
}

