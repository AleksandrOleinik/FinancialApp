package com.example.test

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.time.LocalDate


@Composable
fun IncomePage(viewModel: IncomeViewModel,navController: NavHostController) {
    val incomeList = viewModel.incomeList.observeAsState(emptyList())


    Log.d("IncomeDebug", "Income List in UI: ${incomeList.value}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection()

        LineChartScreen(incomeData = incomeList.value)

        Spacer(modifier = Modifier.height(16.dp))

        incomeList.value.forEach { income ->
            Text(text = "${income.cur}: \$${income.amount}")
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
