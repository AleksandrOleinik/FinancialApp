package com.example.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun BudgetPage(navController: NavHostController) {
    val categories = listOf("Housing", "Food", "Savings", "Other", "Fun")
    val colors = listOf(Color(0xFF78C257), Color(0xFFB5E48C), Color(0xFFE9FF70), Color(0xFFFFC107), Color(0xFFFFA726))
    val increment = 100
    val budgetValues = remember { categories.map { mutableStateOf(1000) } }
    val income = remember { mutableStateOf(5000) } // Dynamic income value
    val totalBudget = remember { derivedStateOf { income.value - budgetValues.sumOf { it.value } } }

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
            //Text(
            //    text = "Budget Distribution",
             //   fontSize = 20.sp,
              //  style = MaterialTheme.typography.bodyMedium,
               // modifier = Modifier.padding(bottom = 16.dp)
            //)

            PieChart(
                data = budgetValues.map { it.value.toFloat() } + totalBudget.value.toFloat(),
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
                    onClick = {
                        if (income.value - increment >= budgetValues.sumOf { it.value }) {
                            income.value -= increment
                        }
                    },
                    modifier = Modifier.size(20.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.minus),
                        contentDescription = "Decrease Income"
                    )
                }

                Text(
                    text = "Income: ${income.value} $",
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                IconButton(
                    onClick = { income.value += increment },
                    modifier = Modifier
                        .size(20.dp)
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
                        onClick = {
                            if (budgetValues[index].value > 0) {
                                budgetValues[index].value -= increment
                            }
                        },
                        modifier = Modifier
                            .size(20.dp)

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.minus),
                            contentDescription = "Decrease Expense"
                        )
                    }

                    Text(
                        text = "$category: ${budgetValues[index].value} $",
                        color = Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )

                    IconButton(
                        onClick = {
                            if (totalBudget.value >= increment) {
                                budgetValues[index].value += increment
                            }
                        },
                        modifier = Modifier
                            .size(20.dp)

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
                text = "Left: ${totalBudget.value} $",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
