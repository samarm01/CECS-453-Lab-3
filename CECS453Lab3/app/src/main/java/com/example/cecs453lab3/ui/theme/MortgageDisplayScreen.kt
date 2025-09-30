package com.example.cecs453lab3.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cecs453lab3.viewmodel.MortgageViewModel // <-- UPDATED IMPORT

@Composable
fun MortgageDisplayScreen(
    viewModel: MortgageViewModel,
    onModifyDataClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    @Composable
    fun DataRow(label: String, value: String, isSpecial: Boolean = false) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, style = MaterialTheme.typography.bodyLarge)
            // Display calculated/important values in a different color
            Text(
                value,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = if (isSpecial) Color.Red else MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "MortgageV0",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
            DataRow("Amount", uiState.amount)
            DataRow("Years", uiState.years)
            DataRow("Interest Rate", uiState.interestRate)

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            DataRow("Monthly Payment", uiState.monthlyPayment, isSpecial = true)
            DataRow("Total Payment", uiState.totalPayment, isSpecial = true)
        }

        // MODIFY DATA Button
        Button(
            onClick = onModifyDataClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("MODIFY DATA")
        }
    }
}