package com.example.test

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun InvestPage(viewModel: InvestViewModel = viewModel(), navController: NavHostController) {
    val investments by viewModel.investments.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection(navController = navController)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Type", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("Ticker", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("Bought at", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("Amount", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("Current Price", fontWeight = FontWeight.Bold, modifier = Modifier.weight(2f))
        }


        investments.forEach { entry ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFEFEF))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(entry.type, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text(entry.ticker, fontSize = 16.sp, modifier = Modifier.weight(1f))
                    Text(entry.boughtAt.toString(), fontSize = 16.sp, modifier = Modifier.weight(1f))
                    Text(entry.amount.toString(), fontSize = 16.sp, modifier = Modifier.weight(1f))
                    Text(entry.price.toString(), fontSize = 16.sp, modifier = Modifier.weight(1f))


                    IconButton(
                        onClick = { viewModel.deleteInvestment(entry) },
                        modifier = Modifier.size(18.dp)
                                            .weight(1f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.minus),
                            contentDescription = "Delete",
                            tint = Color.Black,

                        )
                    }
                }
            }
        }


        AddInvestmentForm(viewModel)
    }
}

@Composable
fun AddInvestmentForm(viewModel: InvestViewModel) {
    var ticker by remember { mutableStateOf("") }
    var boughtAt by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Column (horizontalAlignment = Alignment.CenterHorizontally){
            DropdownMenuExample(
                selectedType = type,
                onTypeSelected = { type = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = ticker,
                onValueChange = { ticker = it },
                label = { Text("Ticker") },
                modifier = Modifier.width(150.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = boughtAt,
                onValueChange = { boughtAt = it },
                label = { Text("Bought at") },
                modifier = Modifier.width(150.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                modifier = Modifier.width(150.dp)
            )
        }


        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                viewModel.addInvestment(
                    InvestEntry(
                        type = type,
                        ticker = ticker,
                        boughtAt = boughtAt.toDoubleOrNull() ?: 0.0,
                        amount = amount.toDoubleOrNull() ?: 0.0
                    )
                )
            },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF227C9D))) {
                Text("Add")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                viewModel.fetchAndUpdatePrice(ticker, type, boughtAt.toDoubleOrNull() ?: 0.0)
            },colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF227C9D))) {
                Text("Update Prices")
            }
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