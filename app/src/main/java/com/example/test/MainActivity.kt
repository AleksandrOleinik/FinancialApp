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

            val database = AppDatabase.getInstance(this)
            val incomeDao = database.incomeDao()
            val expenseDao = database.expenseDao()
            val budgetDao = database.budgetDao()
            val investmentDao = database.investmentDao()

            val incomeRepository = IncomeRepository(incomeDao)
            val expenseRepository = ExpenseRepository(expenseDao)
            val budgetRepository = BudgetRepository(budgetDao)
            val investmentRepository = InvestmentRepository(investmentDao)

            val incomeViewModel: IncomeViewModel = viewModel(factory = IncomeViewModelFactory(incomeRepository))
            val expenseViewModel: ExpenseViewModel = viewModel(factory = ExpenseViewModelFactory(expenseRepository))
            val investViewModel: InvestViewModel = viewModel(factory = InvestViewModelFactory(investmentRepository))
            val budgetViewModel: BudgetViewModel = viewModel(factory = BudgetViewModelFactory(budgetRepository))

            // Pass ViewModels to the App Navigator
            AppNavigator(
                incomeViewModel = incomeViewModel,
                expenseViewModel = expenseViewModel,
                investViewModel = investViewModel,
                budgetViewModel = budgetViewModel
            )
        }
    }
}


@Composable
fun AppNavigator(
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel,
    investViewModel: InvestViewModel,
    budgetViewModel: BudgetViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") { FinancialDashboard(navController) }
        composable("details") { DetailsScreen(navController) }
        composable("budget") {
            BudgetPage(navController = navController, viewModel = budgetViewModel) // Use the existing budgetViewModel
        }
        composable("income") { IncomePage(viewModel = incomeViewModel, navController = navController) }
        composable("expenses") { ExpensesPage(viewModel = expenseViewModel, navController = navController) }
        composable("invest") {
            InvestPage(viewModel = investViewModel, navController = navController)
        }
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



