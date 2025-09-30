package com.example.cecs453lab3.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.cecs453lab3.viewmodel.MortgageViewModel

@Composable
fun MortgageInputScreen(
    viewModel: MortgageViewModel,
    onDoneClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val yearOptions = listOf(10, 15, 30) // Years options from the design

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

        // 1. Years Radio Buttons
        Text("Years", style = MaterialTheme.typography.titleLarge)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            yearOptions.forEach { year ->
                Row(
                    modifier = Modifier.clickable { viewModel.updateInputYears(year) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (year == uiState.inputYears),
                        onClick = { viewModel.updateInputYears(year) }
                    )
                    Text(text = year.toString())
                }
            }
        }

        // 2. Amount Input
        OutlinedTextField(
            value = uiState.inputAmount,
            onValueChange = viewModel::updateInputAmount,
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        // 3. Interest Rate Input
        OutlinedTextField(
            value = uiState.inputRate,
            onValueChange = viewModel::updateInputRate,
            label = { Text("Interest Rate (e.g., 0.035)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom

        // DONE Button
        Button(
            onClick = onDoneClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("DONE")
        }
    }
}