package com.example.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BudgetPage(
    navController: NavHostController,
    viewModel: BudgetViewModel = viewModel(factory = BudgetViewModelFactory(BudgetRepository(AppDatabase.getInstance(navController.context).budgetDao())))
) {
    val budgets by viewModel.budgets.collectAsState()
    val totalBudget by viewModel.totalBudget.collectAsState()

    BudgetPageContent(
        navController = navController,
        budgets = budgets,
        totalBudget = totalBudget,
        onIncrease = { category -> viewModel.increaseExpense(category, 100) },
        onDecrease = { category -> viewModel.decreaseExpense(category, 100) },
        onIncomeIncrease = { viewModel.increaseExpense("Income", 100) },
        onIncomeDecrease = { viewModel.decreaseExpense("Income", 100) },
        onReset = { viewModel.resetBudgets() }
    )
}
@Composable
fun BudgetPageContent(
    navController: NavHostController,
    budgets: List<Budget>,
    totalBudget: Int,
    onIncrease: (String) -> Unit,
    onDecrease: (String) -> Unit,
    onIncomeIncrease: () -> Unit,
    onIncomeDecrease: () -> Unit,
    onReset: () -> Unit
) {
    val income = budgets.find { it.category == "Income" }?.amount ?: 0
    val expenses = budgets.filter { it.category != "Income" }

    // Calculate the remaining budget
    val remainingBudget = totalBudget

    // Prepare the data for the pie chart
    val pieChartData = expenses.map { it.amount.toFloat() } + remainingBudget.toFloat()
    val pieChartLabels = expenses.map { it.category } + "Left"
    val pieChartColors = listOf(
        Color(0xFF78C257), Color(0xFFB5E48C), Color(0xFFFFC107),
        Color(0xFFFFA726), Color(0xFFE9FF70), Color.Gray
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        HeaderSection(navController = navController)

        // PieChart
        PieChart(
            data = pieChartData,
            colors = pieChartColors,
            labels = pieChartLabels,
            modifier = Modifier
                .height(250.dp)
                .padding(vertical = 8.dp)
                .width(250.dp)
        )

        // Income Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4CAF50), RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onIncomeDecrease) {
                Icon(
                    painterResource(id = R.drawable.minus),
                    contentDescription = "Decrease Income",
                    tint = Color.White
                )
            }
            Text(
                text = "Income: $income $",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            IconButton(onClick = onIncomeIncrease) {
                Icon(
                    painterResource(id = R.drawable.plus),
                    contentDescription = "Increase Income",
                    tint = Color.White
                )
            }
        }

        // Expense Rows
        expenses.forEach { budget ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFC107), RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onDecrease(budget.category) }) {
                    Icon(
                        painterResource(id = R.drawable.minus),
                        contentDescription = "Decrease Expense",
                        tint = Color.Black
                    )
                }
                Text(
                    text = "${budget.category}: ${budget.amount} $",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                IconButton(onClick = { onIncrease(budget.category) }) {
                    Icon(
                        painterResource(id = R.drawable.plus),
                        contentDescription = "Increase Expense",
                        tint = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Total Left
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4CAF50), RoundedCornerShape(20.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Left: $totalBudget $",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Reset Button
        Button(
            onClick = onReset,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Reset Budgets")
        }
    }
}

