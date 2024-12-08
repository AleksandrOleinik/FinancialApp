package com.example.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.time.LocalDate


@Composable
fun IncomePage(viewModel: IncomeViewModel,navController: NavHostController) {
    val incomeList = viewModel.incomeList.observeAsState(emptyList())

    println("Debug: Income List = ${incomeList.value}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection()

        LineChartScreen()

        Spacer(modifier = Modifier.height(16.dp))

        incomeList.value.forEach { income ->
            Text(text = "${income.cur}: \$${income.amount}")
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = {
            viewModel.addIncome(Income(3, LocalDate.now(), 1000.0, "USD"))
        }) {
            Text(text = "Add Income")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text(text = "Go Back")
        }
    }
}
