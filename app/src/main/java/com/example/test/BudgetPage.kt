package com.example.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState



@Composable
fun BudgetPage(
    navController: NavHostController,
    viewModel: BudgetViewModel = viewModel(factory = BudgetViewModelFactory(BudgetRepository()))
) {
    val income = viewModel.income.collectAsState()
    val budgetValues = viewModel.budgetValues.collectAsState()
    val totalBudget = viewModel.totalBudget.collectAsState()

    BudgetPageContent(
        navController = navController,
        income = income.value,
        budgetValues = budgetValues.value,
        totalBudget = totalBudget.value,
        categories = viewModel.categories,
        colors = viewModel.colors,
        viewModel = viewModel
    )
}

@Composable
fun BudgetPageContent(
    navController: NavHostController,
    income: Int,
    budgetValues: List<Int>,
    totalBudget: Int,
    categories: List<String>,
    colors: List<Color>,
    viewModel: BudgetViewModel
) {
    val increment = 100

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection(navController = navController)

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PieChart(
                data = budgetValues.map { it.toFloat() } + totalBudget.toFloat(),
                colors = colors + listOf(Color.Gray),
                labels = categories + listOf("Left"),
                modifier = Modifier.size(200.dp)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF78C257), RoundedCornerShape(20.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(20.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { viewModel.decreaseIncome(increment) },
                    modifier = Modifier.size(20.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.minus),
                        contentDescription = "Decrease Income"
                    )
                }

                Text(
                    text = "Income: $income $",
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                IconButton(
                    onClick = { viewModel.increaseIncome(increment) },
                    modifier = Modifier.size(20.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = "Increase Income"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            categories.forEachIndexed { index, category ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors[index], RoundedCornerShape(20.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(20.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { viewModel.decreaseExpense(index, increment) },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.minus),
                            contentDescription = "Decrease Expense"
                        )
                    }

                    Text(
                        text = "$category: ${budgetValues[index]} $",
                        color = Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )

                    IconButton(
                        onClick = { viewModel.increaseExpense(index, increment) },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.plus),
                            contentDescription = "Increase Expense"
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF78C257), RoundedCornerShape(20.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(20.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Left: $totalBudget $",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
