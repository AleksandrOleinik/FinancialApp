package com.example.test


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun FinancialDashboard(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
            .padding(30.dp)
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
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DashboardButton(
            text = "Income",
            backgroundColor = Color(0xFF4CAF50),
            onClick = { navController.navigate("income") }
        )
        DashboardButton(
            text = "Expenses",
            backgroundColor = Color(0xFFFFC107),
            onClick = { navController.navigate("expenses") }
        )
        DashboardButton(
            text = "Budget",
            backgroundColor = Color(0xFF227C9D),
            onClick = { navController.navigate("budget") }
        )
        DashboardButton(
            text = "Investments",
            backgroundColor = Color(0xFF4CAF50),
            onClick = { navController.navigate("invest") }
        )
    }
}

@Composable
fun DashboardButton(text: String, backgroundColor: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .width(300.dp)
            .background(backgroundColor, RoundedCornerShape(20.dp))
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            textAlign = TextAlign.Center
        )
    }
}

