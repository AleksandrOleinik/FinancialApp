package com.example.test

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun InvestPage(viewModel: InvestViewModel = viewModel()) {

    val response = viewModel.response.value
    val errorMessage = viewModel.errorMessage.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Invest Data Viewer",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (errorMessage != null) {
            Text(
                text = "Error: $errorMessage",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        } else if (response != null) {
            Text(
                text = """
                    Status: ${response.status}
                    From: ${response.from}
                    Symbol: ${response.symbol}
                    Open: ${response.open}
                    High: ${response.high}
                    Low: ${response.low}
                    Close: ${response.close}
                    Volume: ${response.volume}
                    After Hours: ${response.afterHours ?: "N/A"}
                    Pre Market: ${response.preMarket ?: "N/A"}
                """.trimIndent(),
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            Button(onClick = {
                viewModel.fetchDailyOpenClose("AAPL", "2024-12-12")
            }) {
                Text(text = "Fetch Data")
            }
        }
    }
}
