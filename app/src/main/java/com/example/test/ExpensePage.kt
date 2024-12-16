package com.example.test

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.time.format.DateTimeFormatter

@Composable
fun ExpensesPage(viewModel: ExpenseViewModel, navController: NavHostController) {
    val expenseList by viewModel.expenseList.collectAsState()

    val itemsPerPage = 5
    val totalPages = (expenseList.size + itemsPerPage - 1) / itemsPerPage
    var currentPage by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            HeaderSection(navController = navController)
            LineChartScreen(
                data = expenseList.map {
                    Pair(it.date.format(DateTimeFormatter.ofPattern("MM/yy")), it.amount.toFloat())
                },
                label = "Expenses",
                lineColor = android.graphics.Color.RED
            )

            Spacer(modifier = Modifier.height(16.dp))

            val startIndex = currentPage * itemsPerPage
            val endIndex = (startIndex + itemsPerPage).coerceAtMost(expenseList.size)
            val paginatedItems = expenseList.subList(startIndex, endIndex)

            paginatedItems.forEach { expense ->
                StyledItem(
                    Date = expense.date.format(DateTimeFormatter.ofPattern("MM/yy")),
                    curr = expense.cur,
                    amount = expense.amount.toString(),
                    onDelete = {
                        viewModel.deleteExpense(expense.id)
                    }
                )
            }

        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { if (currentPage > 0) currentPage-- },
                    enabled = currentPage > 0,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF227C9D))
                ) {
                    Text("Previous")
                }

                Button(
                    onClick = { if (currentPage < totalPages - 1) currentPage++ },
                    enabled = currentPage < totalPages - 1,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF227C9D))
                ) {
                    Text("Next")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AddExpenseButton(
                onSubmit = { newExpense ->
                    viewModel.addExpense(newExpense)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.navigateUp() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF227C9D))) {
                Text(text = "Go Back")
            }
        }
    }
}
