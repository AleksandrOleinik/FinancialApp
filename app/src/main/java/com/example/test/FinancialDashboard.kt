package com.example.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
            HeaderSection()
            ButtonList(navController)
            AddExpenseButton()
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
            onClick = { navController.navigate("details") } // Navigate to details screen
        )
        DashboardButton(
            text = "Expenses",
            backgroundColor = Color(0xFFF4A261),
            onClick = { navController.navigate("details") }
        )
        DashboardButton(
            text = "Budget",
            backgroundColor = Color(0xFF82B4E3),
            onClick = { navController.navigate("details") }
        )
        DashboardButton(
            text = "Investments",
            backgroundColor = Color(0xFFB4D881),
            onClick = { navController.navigate("details") }
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


@Composable
fun AddExpenseButton() {
    Button(
        onClick = { /* TODO: Add action */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(width = 2.dp, color = Color.Black, shape = MaterialTheme.shapes.extraLarge),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4A261))
    ) {
        Text(
            text = "Add Expense",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }
}