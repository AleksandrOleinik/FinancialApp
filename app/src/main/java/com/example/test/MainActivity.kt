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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun FinancialDashboardPreview() {
    FinancialDashboard()
}


@Composable
fun FinancialDashboard() {
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
            ButtonList()
            AddExpenseButton()
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.home),
            contentDescription = "Home",
            modifier = Modifier.size(32.dp),
            tint = Color.Black
        )
        Text(
            text = "The one who walks\nwill master the road",
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Profile",
            modifier = Modifier.size(32.dp),
            tint = Color.Black
        )
    }
}

@Composable
fun ButtonList() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DashboardButton(text = "Income", backgroundColor = Color(0xFFB4D881))
        DashboardButton(text = "Expenses", backgroundColor = Color(0xFFF4A261))
        DashboardButton(text = "Budget", backgroundColor = Color(0xFF82B4E3))
        DashboardButton(text = "Investments", backgroundColor = Color(0xFFB4D881))
    }
}

@Composable
fun DashboardButton(text: String, backgroundColor: Color) {
    Button(
        onClick = { /* TODO: Add navigation or action */ },
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

