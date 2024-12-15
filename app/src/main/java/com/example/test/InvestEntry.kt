package com.example.test
import androidx.room.Entity
import androidx.room.PrimaryKey

data class InvestEntry(
    val type: String,
    val ticker: String,
    var boughtAt: Double,
    var amount: Double,
    var price: Double = 0.0
)



@Entity(tableName = "investments")
data class Investment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val ticker: String,
    val boughtAt: Double,
    val amount: Double,
    var price: Double = 0.0
)
