package com.example.cecs453lab3.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.cecs453lab3.model.Mortgage

// Data class to represent the UI state
data class MortgageUiState(
    // Display fields
    val amount: String = "",
    val years: String = "",
    val interestRate: String = "", // e.g., 3.5%
    val monthlyPayment: String = "",
    val totalPayment: String = "",
    // Input fields state (used by the input screen)
    val inputAmount: String = "",
    val inputRate: String = "",
    val inputYears: Int = 30
)

class MortgageViewModel : ViewModel() {

    private val mortgageModel = Mortgage()

    private val _uiState = MutableStateFlow(mapModelToUiState(mortgageModel))
    val uiState: StateFlow<MortgageUiState> = _uiState.asStateFlow()

    private fun mapModelToUiState(model: Mortgage): MortgageUiState {
        // Convert percentage for display: .035 -> 3.5%
        val displayRate = String.format("%.1f%%", model.getRate() * 100)

        return MortgageUiState(
            amount = model.getFormattedAmount() ?: "$0.00",
            years = model.getFormattedYears(),
            interestRate = displayRate,
            monthlyPayment = model.formattedMonthlyPayment() ?: "$0.00",
            totalPayment = model.formattedTotalPayment() ?: "$0.00",
            inputAmount = model.getAmount().toString(),
            inputRate = model.getFormattedRate(),
            inputYears = model.getYears()
        )
    }

    // --- Functions for Input Screen to update parameters (user changes) ---
    fun updateInputAmount(newAmount: String) {
        _uiState.update { it.copy(inputAmount = newAmount) }
    }

    fun updateInputRate(newRate: String) {
        _uiState.update { it.copy(inputRate = newRate) }
    }

    fun updateInputYears(newYears: Int) {
        _uiState.update { it.copy(inputYears = newYears) }
    }

    // Function called by the 'DONE' button to apply changes and recalculate
    fun applyMortgageData() {
        // Attempt to parse new values
        val newAmount = _uiState.value.inputAmount.toFloatOrNull() ?: 0f
        val newRate = _uiState.value.inputRate.toFloatOrNull() ?: 0f
        val newYears = _uiState.value.inputYears

        // Update the Model
        mortgageModel.setAmount(newAmount)
        mortgageModel.setRate(newRate)
        mortgageModel.setYears(newYears)

        // Update the UI state with the new calculated/formatted values
        _uiState.update { mapModelToUiState(mortgageModel) }
    }
}