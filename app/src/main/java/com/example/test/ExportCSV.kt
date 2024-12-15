package com.example.test

import android.content.Context
import android.os.Environment
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import androidx.compose.runtime.*

import android.content.Intent
import androidx.core.content.FileProvider

@Composable
fun ExportCsvButton(context: Context, database: AppDatabase) {
    var exportStatus by remember { mutableStateOf<ExportStatus>(ExportStatus.Idle) }

    Button(
        onClick = {
            exportStatus = ExportStatus.Loading
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val file = exportAllTablesToCsv(context, database) // Returns the file
                    exportStatus = ExportStatus.Success(file)
                } catch (e: Exception) {
                    exportStatus = ExportStatus.Failure(e.message ?: "Unknown error")
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(width = 2.dp, color = Color.Black, shape = MaterialTheme.shapes.extraLarge),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4A261))
    ) {
        Text(
            text = "Export CSV",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }

    when (exportStatus) {
        is ExportStatus.Loading -> Text("Exporting...", modifier = Modifier.padding(top = 16.dp))
        is ExportStatus.Success -> {
            val file = (exportStatus as ExportStatus.Success).file
            Column {
                Text("Exported to: ${file.absolutePath}", modifier = Modifier.padding(top = 16.dp))
                Button(
                    onClick = { shareCsvFile(context, file) },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Send via Email")
                }
            }
        }
        is ExportStatus.Failure -> Text("Export Failed: ${(exportStatus as ExportStatus.Failure).error}", modifier = Modifier.padding(top = 16.dp))
        else -> {} // Do nothing for Idle state
    }
}


sealed class ExportStatus {
    object Idle : ExportStatus()
    object Loading : ExportStatus()
    data class Success(val file: File) : ExportStatus()
    data class Failure(val error: String) : ExportStatus()
}



suspend fun exportAllTablesToCsv(context: Context, database: AppDatabase): File {
    val csvDirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "FinancialExports")
    if (!csvDirectory.exists()) {
        csvDirectory.mkdirs()
    }

    val file = File(csvDirectory, "financial_data.csv")

    try {
        val incomes = database.incomeDao().getAllIncomes()
        val expenses = database.expenseDao().getAllExpenses()
        val budgets = database.budgetDao().getAllBudgets()
        val investments = database.investmentDao().getAllInvestments().first() // Fetch Flow data

        // Generate CSV content
        val writer = FileWriter(file)
        writer.append("Category,Type,Amount,Additional Info\n")

        // Write Incomes
        writer.append("Income\n")
        incomes.forEach {
            writer.append("${it.id},Income,${it.amount},Date:${it.date}\n")
        }

        // Write Expenses
        writer.append("Expenses\n")
        expenses.forEach {
            writer.append("${it.id},Expense,${it.amount},Date:${it.date}\n")
        }

        // Write Budgets
        writer.append("Budgets\n")
        budgets.forEach {
            writer.append("${it.id},Budget,${it.amount},${it.category}\n")
        }

        // Write Investments
        writer.append("Investments\n")
        investments.forEach {
            writer.append("${it.id},Investment,${it.amount},${it.ticker} at ${it.boughtAt}\n")
        }

        writer.flush()
        writer.close()

        println("CSV File Saved: ${file.absolutePath}")
        return file
    } catch (e: Exception) {
        throw Exception("Error exporting CSV: ${e.message}")
    }
}
fun shareCsvFile(context: Context, file: File) {
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

    val emailIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/csv"
        putExtra(Intent.EXTRA_EMAIL, arrayOf("")) // Pre-fill recipient (optional)
        putExtra(Intent.EXTRA_SUBJECT, "Exported Financial Data")
        putExtra(Intent.EXTRA_TEXT, "Please find the exported CSV file attached.")
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant temporary access to the file
    }

    // Launch the email app
    context.startActivity(Intent.createChooser(emailIntent, "Send CSV via Email"))
}
