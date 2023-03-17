package com.example.billbuddy.data.local

import androidx.room.*
import com.example.billbuddy.data.local.model.Expense
import com.example.billbuddy.data.local.model.Payment
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)


    @Query("SELECT * FROM expense_table WHERE id = :id")
    suspend fun getExpenseById(id: Int): Expense?

    @Query("SELECT * FROM expense_table")
    fun getAllExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE expenseDate BETWEEN datetime('now', '-7 day') AND datetime('now', 'localtime')")
    fun getWeeklyExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE expenseDate BETWEEN datetime('now', '-1 month') AND datetime('now', 'localtime')")
    fun getMonthlyExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE expenseDate >= datetime('now', '-3 day') AND expenseDate < datetime('now', 'localtime') ")
    fun get3DayExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE expenseDate >= datetime('now', '-14 day') AND expenseDate < datetime('now', 'localtime')")
    fun get14DayExpenses(): Flow<List<Expense>>

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("SELECT * FROM expense_table WHERE expenseDate BETWEEN :startDate AND :endDate")
   suspend fun getExpensesForMonthRange(startDate: LocalDateTime, endDate: LocalDateTime): List<Expense>


}