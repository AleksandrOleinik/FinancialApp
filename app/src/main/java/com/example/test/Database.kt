package com.example.test

import androidx.room.*
import androidx.room.TypeConverter
import java.time.LocalDate
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Query("SELECT * FROM expenses")
    suspend fun getAllExpenses(): List<Expense>

    @Query("DELETE FROM expenses WHERE id = :expenseId")
    suspend fun deleteExpense(expenseId: Int): Int // Return the number of rows deleted
}

@Dao
interface IncomeDao {
    @Insert
    suspend fun insertIncome(income: Income)

    @Query("SELECT * FROM incomes")
    suspend fun getAllIncomes(): List<Income>

    @Query("DELETE FROM incomes WHERE id = :incomeId")
    suspend fun deleteIncome(incomeId: Int)
}

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budgets")
    suspend fun getAllBudgets(): List<Budget>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: Budget)

    @Update
    suspend fun updateBudget(budget: Budget)

    @Query("DELETE FROM budgets")
    suspend fun resetBudgets()
}
@Dao
interface InvestmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvestment(investment: Investment)

    @Query("SELECT * FROM investments")
    fun getAllInvestments(): Flow<List<Investment>>

    @Query("DELETE FROM investments WHERE id = :investmentId")
    suspend fun deleteInvestmentById(investmentId: Int)

    @Update
    suspend fun updateInvestment(investment: Investment)

    @Query("DELETE FROM investments")
    suspend fun deleteAll()
}

class Converters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.format(formatter)
    }

    @TypeConverter
    fun toLocalDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString, formatter)
    }
}
@Database(
    entities = [Expense::class, Income::class, Budget::class, Investment::class], // Added Investment
    version = 3, // Incremented version to reflect the schema change
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun incomeDao(): IncomeDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun budgetDao(): BudgetDao
    abstract fun investmentDao(): InvestmentDao // Added DAO for investments

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "financial_database"
                )
                    .fallbackToDestructiveMigration() // Automatically handle migration
                    .addCallback(PrepopulateCallback(context)) // Prepopulate data
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class PrepopulateCallback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                // Initialize the database with default data
                getInstance(context).budgetDao().insertBudget(
                    Budget(category = "Default", amount = 1000)
                )
                // Add more prepopulation logic if needed
            }
        }
    }
}
