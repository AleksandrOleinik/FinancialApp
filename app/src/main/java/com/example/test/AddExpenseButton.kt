package com.example.test

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AddExpenseButton(
    onSubmit: (Expense) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isAddingExpense by remember { mutableStateOf(false) }
    var dateInput by remember { mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) }
    var amountInput by remember { mutableStateOf("") }
    var currencyInput by remember { mutableStateOf("") }

    if (isAddingExpense) {
        Column(modifier = modifier) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
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
                }


                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            onSubmit(
                                Expense(
                                    id = 0,
                                    date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                    amount = amountInput.toDoubleOrNull() ?: 0.0,
                                    cur = currencyInput
                                )
                            )
                            isAddingExpense = false
                        },colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF227C9D))
                    ) {
                        Text("Submit")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            isAddingExpense = false
                            dateInput = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                            amountInput = ""
                            currencyInput = ""
                        },colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF227C9D))
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    } else {
        Button(onClick = { isAddingExpense = true }, modifier = modifier,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF227C9D))) {
            Text(text = "Add Expense")
        }
    }
}

