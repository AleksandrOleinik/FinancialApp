package com.example.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StyledItem(
    Date: String,
    curr: String,
    amount: String,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .height(45.dp)
            .width(300.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$Date: $curr $amount",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )


            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(36.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.minus),
                    contentDescription = "Delete",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

