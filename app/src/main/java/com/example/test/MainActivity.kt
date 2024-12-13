package com.example.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val incomeRepository = IncomeRepository()
            val expenseRepository = ExpenseRepository()
            val incomeViewModel: IncomeViewModel = viewModel(factory = IncomeViewModelFactory(incomeRepository))
            val expenseViewModel: ExpenseViewModel = viewModel(factory = ExpenseViewModelFactory(expenseRepository))

            AppNavigator(
                incomeViewModel,
                expenseViewModel
            )

        }
    }
}



@Composable
fun AppNavigator(incomeViewModel: IncomeViewModel, expenseViewModel: ExpenseViewModel) {
    val navController = rememberNavController()
    val budgetRepository = remember { BudgetRepository() }
    val budgetViewModelFactory = remember { BudgetViewModelFactory(budgetRepository) }

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") { FinancialDashboard(navController) }
        composable("details") {  DetailsScreen(navController)  }
        composable("budget") {
            val budgetViewModel = viewModel<BudgetViewModel>(factory = budgetViewModelFactory)
            BudgetPage(navController = navController, viewModel = budgetViewModel)
        }
        composable("income") { IncomePage(viewModel = incomeViewModel, navController = navController) }
        composable("expenses") { ExpensesPage(viewModel = expenseViewModel, navController = navController) }

    }
}


@Composable //this is just for testing changing routes. will be replaced for correct route
fun DetailsScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "This is the Details Page",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigateUp() }) {
                Text(text = "Go Back")
            }
        }
    }
}



