package com.example.billbuddy.domain.repository

import com.example.billbuddy.data.local.model.Expense
import com.example.billbuddy.util.Resource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface ExpenseRepository {

    suspend fun insertExpense(expense: Expense)

    fun getAllExpenses(): Flow<Resource<List<Expense>>>

    fun getWeeklyExpenses(): Flow<Resource<List<Expense>>>

    fun getMonthlyExpenses(): Flow<Resource<List<Expense>>>

    fun get3DayExpenses(): Flow<Resource<List<Expense>>>

    fun get14DayExpenses(): Flow<Resource<List<Expense>>>

    suspend fun deleteExpense(expense: Expense)

    suspend fun getExpenseById(id: Int): Expense?

    suspend fun getExpensesForMonthRange(startMonth: LocalDateTime, endMonth: LocalDateTime): List<Expense>
}