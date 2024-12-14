package com.example.test

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun InvestPage(viewModel: InvestViewModel = viewModel(),navController: NavHostController) {
    val investments by viewModel.investments.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {


        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            HeaderSection(navController = navController)
            Row(modifier=Modifier.padding(12.dp),horizontalArrangement = Arrangement.SpaceBetween,){
                Text("Type", modifier = Modifier.weight(2f))
                Text("Ticker", modifier = Modifier.weight(2f))
                Text("Bought at", modifier = Modifier.weight(2f))
                Text("Amount", modifier = Modifier.weight(2f))
                Text("Current Price", modifier = Modifier.weight(2f))
                Text("              ", modifier = Modifier.weight(2f))

            }

            investments.forEach { entry ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(entry.type, modifier = Modifier.weight(1f))
                    Text(entry.ticker, modifier = Modifier.weight(1f))
                    Text(entry.boughtAt.toString(), modifier = Modifier.weight(1f))
                    Text(entry.amount.toString(), modifier = Modifier.weight(1f))
                    Text(entry.price.toString(), modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = { viewModel.deleteInvestment(entry) },
                        modifier = Modifier.size(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.minus),
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
        Column(){

        var ticker by remember { mutableStateOf("") }
        var boughtAt by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        var type by remember { mutableStateOf("") }

        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column {
                DropdownMenuExample(
                    selectedType = type,
                    onTypeSelected = { type = it }
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = ticker,
                    onValueChange = { ticker = it },
                    label = { Text("Ticker") },
                    modifier = Modifier.width(200.dp)
                        .height(60.dp)
                )
            }
            Column {
                TextField(
                    value = boughtAt,
                    onValueChange = { boughtAt = it },
                    label = { Text("Bought at") },
                    modifier = Modifier.width(200.dp)
                        .height(60.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    modifier = Modifier.width(200.dp)
                        .height(60.dp)
                )
            }
        }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ){
        Button(onClick = {
            viewModel.addInvestment(
                InvestEntry(
                    type = type,
                    ticker = ticker,
                    boughtAt = boughtAt.toDoubleOrNull() ?: 0.0,
                    amount = amount.toDoubleOrNull() ?: 0.0
                )
            )
        }) {
            Text("Add")
        }

        Button(onClick = {
            investments.forEach { viewModel.fetchAndUpdatePrice(it.ticker, it.type, it.boughtAt) }
        }) {
            Text("Update Prices")
        }}
    }
        }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuExample(selectedType: String, onTypeSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }


    val options = listOf("Stock", "Crypto", "Cash", "Real Estate")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedType,
            onValueChange = { onTypeSelected(it) },
            label = { Text("Type") },
            readOnly = true,
            modifier = Modifier
                .width(200.dp)
                .height(60.dp)
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onTypeSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
