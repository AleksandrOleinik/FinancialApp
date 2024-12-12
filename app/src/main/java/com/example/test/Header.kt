package com.example.test

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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



@Composable
fun HeaderSection(navController: NavHostController) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 50.dp, start = 24.dp, end = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.home),
            contentDescription = "Home",
            modifier = Modifier
                .size(32.dp)
                .clickable { navController.navigateUp() },
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