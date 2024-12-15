package com.example.test


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun FinancialDashboard(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderSection(navController = navController)
            ButtonList(navController)
            ExportCsvButton(context = navController.context, database = AppDatabase.getInstance(navController.context))
        }
    }
}

@Composable
fun ButtonList(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DashboardButton(
            text = "Income",
            backgroundColor = Color(0xFFB4D881),
            onClick = { navController.navigate("income") }
        )
        DashboardButton(
            text = "Expenses",
            backgroundColor = Color(0xFFF4A261),
            onClick = { navController.navigate("expenses") }
        )
        DashboardButton(
            text = "Budget",
            backgroundColor = Color(0xFF82B4E3),
            onClick = { navController.navigate("budget") }
        )
        DashboardButton(
            text = "Investments",
            backgroundColor = Color(0xFFB4D881),
            onClick = { navController.navigate("invest") }
        )
    }
}

@Composable
fun DashboardButton(text: String, backgroundColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(width = 2.dp, color = Color.Black, shape = MaterialTheme.shapes.extraLarge),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }
}


