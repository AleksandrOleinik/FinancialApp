package com.example.test

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AddButton(
    onSubmit: (Income) -> Unit,
    modifier: Modifier = Modifier
) {
    var isAddingIncome by remember { mutableStateOf(false) }
    var dateInput by remember { mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) }
    var amountInput by remember { mutableStateOf("") }
    var currencyInput by remember { mutableStateOf("") }

    if (isAddingIncome) {
        Column(modifier = modifier) {
            OutlinedTextField(
                value = dateInput,
                onValueChange = { dateInput = it },
                label = { Text("Date (yyyy-MM-dd)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = amountInput,
                onValueChange = { amountInput = it },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = currencyInput,
                onValueChange = { currencyInput = it },
                label = { Text("Currency") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    // Pass the income object back to the parent
                    onSubmit(
                        Income(
                            id = 0, // Parent should handle unique IDs
                            date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            amount = amountInput.toDoubleOrNull() ?: 0.0,
                            cur = currencyInput
                        )
                    )
                    isAddingIncome = false
                }) {
                    Text("Submit")
                }

                Button(onClick = {
                    // Reset the state
                    isAddingIncome = false
                    dateInput = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    amountInput = ""
                    currencyInput = ""
                }) {
                    Text("Cancel")
                }
            }
        }
    } else {
        Button(onClick = { isAddingIncome = true }, modifier = modifier) {
            Text(text = "Add Income")
        }
    }
}
