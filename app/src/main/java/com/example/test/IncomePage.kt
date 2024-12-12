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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.time.format.DateTimeFormatter

@Composable
fun IncomePage(viewModel: IncomeViewModel, navController: NavHostController) {
    val incomeList by viewModel.incomeList.collectAsState()

    val itemsPerPage = 5
    val totalPages = (incomeList.size + itemsPerPage - 1) / itemsPerPage
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
            LineChartScreen(incomeData = incomeList)

            Spacer(modifier = Modifier.height(16.dp))

            val startIndex = currentPage * itemsPerPage
            val endIndex = (startIndex + itemsPerPage).coerceAtMost(incomeList.size)
            val paginatedItems = incomeList.subList(startIndex, endIndex)

            paginatedItems.forEach { income ->
                StyledItem(
                    Date = income.date.format(DateTimeFormatter.ofPattern("MM/yy")),
                    curr = income.cur,
                    amount = income.amount.toString()
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
                enabled = currentPage > 0
            ) {
                Text("Previous")
            }

            //Text("Page ${currentPage + 1} of $totalPages")

            Button(
                onClick = { if (currentPage < totalPages - 1) currentPage++ },
                enabled = currentPage < totalPages - 1
            ) {
                Text("Next")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AddButton(
            onSubmit = { newIncome ->
                viewModel.addIncome(newIncome)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigateUp() }) {
            Text(text = "Go Back")
        }
        }
    }
}
