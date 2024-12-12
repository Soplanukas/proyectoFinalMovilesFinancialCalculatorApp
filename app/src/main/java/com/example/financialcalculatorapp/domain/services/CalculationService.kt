package com.example.financialcalculatorapp.domain.services
import com.example.financialcalculatorapp.domain.models.CalculationCategory

class CalculationService {
    fun calculateProductMetrics(basePrice: Double, cost: Double): CalculationCategory.ProductCalculation {
        val priceWithIVA = basePrice * 1.19
        val profitMargin = ((priceWithIVA - cost) / priceWithIVA) * 100
        val breakEvenPoint = cost / (priceWithIVA - cost)
        val roi = ((priceWithIVA - basePrice) / basePrice) * 100

        return CalculationCategory.ProductCalculation(
            basePrice = basePrice,
            cost = cost,
            priceWithIVA = priceWithIVA,
            profitMargin = profitMargin,
            breakEvenPoint = breakEvenPoint,
            roi = roi
        )
    }

    fun calculateEmployerCosts(baseSalary: Double): CalculationCategory.EmployerCalculation {
        val parafiscalContributions = baseSalary * 0.09 // SENA 2%, ICBF 3%, Caja 4%
        val socialSecurity = baseSalary * 0.205 // Pensión 12%, Salud 8.5%
        val socialBenefits = baseSalary * 0.2183 // Prima 8.33%, Cesantías 8.33%, Intereses 1%, Vacaciones 4.17%
        val totalPayrollCost = baseSalary + parafiscalContributions + socialSecurity + socialBenefits

        return CalculationCategory.EmployerCalculation(
            baseSalary = baseSalary,
            parafiscalContributions = parafiscalContributions,
            socialSecurity = socialSecurity,
            socialBenefits = socialBenefits,
            totalPayrollCost = totalPayrollCost
        )
    }

    fun calculateEmployeeSalary(baseSalary: Double, overtimeHours: Double, isNightShift: Boolean, isSundayHoliday: Boolean): CalculationCategory.EmployeeCalculation {
        val pensionDeduction = baseSalary * 0.04
        val healthDeduction = baseSalary * 0.04
        val netSalary = baseSalary - (pensionDeduction + healthDeduction)

        val hourlyRate = baseSalary / 240
        var overtimeCompensation = 0.0
        var overtimeNightCompensation = 0.0
        var overtimeSundayHolidayCompensation = 0.0

        // Cálculos para horas extra
        if (overtimeHours > 0) {
            if (isNightShift) {
                overtimeNightCompensation = overtimeHours * (hourlyRate * 1.75) // Hora extra nocturna
            } else if (isSundayHoliday) {
                overtimeSundayHolidayCompensation = overtimeHours * (hourlyRate * 2.0) // Hora dominical/festiva
            } else {
                overtimeCompensation = overtimeHours * (hourlyRate * 1.25) // Hora extra diurna
            }
        }

        return CalculationCategory.EmployeeCalculation(
            baseSalary = baseSalary,
            netSalary = netSalary,
            payrollDeductions = pensionDeduction + healthDeduction,
            overtimeHours = overtimeHours,
            overtimeCompensation = overtimeCompensation,
            overtimeNightCompensation = overtimeNightCompensation, // Nueva propiedad
            overtimeSundayHolidayCompensation = overtimeSundayHolidayCompensation // Nueva propiedad
        )
    }
}