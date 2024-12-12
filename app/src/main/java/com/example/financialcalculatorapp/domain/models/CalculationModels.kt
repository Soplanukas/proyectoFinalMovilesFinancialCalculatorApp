package com.example.financialcalculatorapp.domain.models

sealed class CalculationCategory {
    data class ProductCalculation(
        val basePrice: Double,
        val cost: Double,
        val priceWithIVA: Double? = null,
        val profitMargin: Double? = null,
        val breakEvenPoint: Double? = null,
        val roi: Double? = null
    ) : CalculationCategory()

    data class EmployerCalculation(
        val baseSalary: Double,
        val parafiscalContributions: Double? = null,
        val socialSecurity: Double? = null,
        val socialBenefits: Double? = null,
        val totalPayrollCost: Double? = null
    ) : CalculationCategory()

    data class EmployeeCalculation(
        val baseSalary: Double,
        val netSalary: Double? = null,
        val payrollDeductions: Double? = null,
        val overtimeHours: Double? = null,
        val overtimeCompensation: Double? = null,
        val overtimeNightCompensation: Double? = null, // Nueva propiedad para horas nocturnas
        val overtimeSundayHolidayCompensation: Double? = null // Nueva propiedad para horas dominicales/festivas
    ) : CalculationCategory()
}