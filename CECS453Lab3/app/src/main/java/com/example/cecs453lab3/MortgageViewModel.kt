package com.example.cecs453lab3

import androidx.lifecycle.ViewModel
import com.example.cecs453lab3.Mortgage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Data class to represent the UI state
data class MortgageUiState(
    val amount: String = "$100,000.00",
    val years: String = "30",
    val interestRate: String = "3.5%",
    val monthlyPayment: String = "$449.04",
    val totalPayment: String = "$161,654.66",
    // Input fields state (used by the input screen)
    val inputAmount: String = "100000.00",
    val inputRate: String = "0.035",
    val inputYears: Int = 30
)

class MortgageViewModel : ViewModel() {

    private val mortgageModel = Mortgage()

    // MutableStateFlow to hold and manage the UI state
    private val _uiState = MutableStateFlow(mapModelToUiState(mortgageModel))
    val uiState: StateFlow<MortgageUiState> = _uiState.asStateFlow()

    private fun mapModelToUiState(model: Mortgage): MortgageUiState {
        // Convert percentage for display: .035 -> 3.5%
        val displayRate = String.format("%.1f%%", model.getRate() * 100)

        return MortgageUiState(
            amount = model.getFormattedAmount() ?: "",
            years = model.getFormattedYears(),
            interestRate = displayRate,
            monthlyPayment = model.formattedMonthlyPayment() ?: "",
            totalPayment = model.formattedTotalPayment() ?: "",
            inputAmount = model.getAmount().toString(), // Used to pre-fill input field
            inputRate = model.getFormattedRate(), // Used to pre-fill input field
            inputYears = model.getYears()
        )
    }

    // --- Functions for Input Screen to update parameters ---

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
        // Attempt to parse and set new values, with simple validation/defaults
        val newAmount = _uiState.value.inputAmount.toFloatOrNull() ?: 0f
        val newRate = _uiState.value.inputRate.toFloatOrNull() ?: 0f
        val newYears = _uiState.value.inputYears

        mortgageModel.setAmount(newAmount)
        mortgageModel.setRate(newRate)
        mortgageModel.setYears(newYears)

        // Update the UI state with the new calculated/formatted values
        _uiState.update { mapModelToUiState(mortgageModel) }
    }
}