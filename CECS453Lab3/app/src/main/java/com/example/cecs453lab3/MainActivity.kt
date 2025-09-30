package com.example.cecs453lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cecs453lab3.navigation.MortgageDestinations
import com.example.cecs453lab3.viewmodel.MortgageViewModel
import com.example.cecs453lab3.ui.theme.MortgageDisplayScreen
import com.example.cecs453lab3.ui.theme.MortgageInputScreen
import com.example.cecs453lab3.ui.theme.CECS453Lab3Theme // (Using the fixed theme name)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CECS453Lab3Theme {
                MortgageAppNavHost()
            }
        }
    }
}

@Composable
fun MortgageAppNavHost(
    // ViewModel is scoped to the NavHost/Activity life-cycle
    viewModel: MortgageViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MortgageDestinations.DISPLAY_ROUTE
    ) {
        composable(MortgageDestinations.DISPLAY_ROUTE) {
            MortgageDisplayScreen(
                viewModel = viewModel,
                onModifyDataClicked = {
                    navController.navigate(MortgageDestinations.INPUT_ROUTE)
                }
            )
        }
        composable(MortgageDestinations.INPUT_ROUTE) {
            MortgageInputScreen(
                viewModel = viewModel,
                onDoneClicked = {
                    // 1. Apply data (updates Model and ViewModel state)
                    viewModel.applyMortgageData()
                    // 2. Navigate back to the display screen
                    navController.popBackStack()
                }
            )
        }
    }
}
