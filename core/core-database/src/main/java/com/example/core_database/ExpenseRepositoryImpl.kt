package com.example.core_database

import com.example.util.model.Expense
import com.example.util.model.InvalidExpenseException
import com.example.billbuddy.util.Resource
import com.example.core_domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {
    override suspend fun insertExpense(expense: Expense) {
        if (expense.expenseAmount.isBlank()) {
            throw InvalidExpenseException(message = "Expense amount can't be empty")
        }
        expenseDao.insertExpense(expense)
    }

    override fun getAllExpenses(): Flow<Resource<List<Expense>>> = flow {
        try {
            emit(Resource.Loading())
            expenseDao.getAllExpenses().collect { expenseList ->
                emit(Resource.Success(expenseList))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Something is wrong"))
        }
    }

    override fun getWeeklyExpenses(): Flow<Resource<List<Expense>>> = flow {
        try {
            emit(Resource.Loading())
            expenseDao.getWeeklyExpenses().collect { weeklyExpenseList ->
                emit(Resource.Success(weeklyExpenseList))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Something is wrong"))
        }
    }

    override fun getMonthlyExpenses(): Flow<Resource<List<Expense>>> = flow {
        try {
            emit(Resource.Loading())
            expenseDao.getMonthlyExpenses().collect { monthlyExpenseList ->
                emit(Resource.Success(monthlyExpenseList))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Something is wrong"))
        }
    }

    override fun get3DayExpenses(): Flow<Resource<List<Expense>>> = flow {
        try {
            emit(Resource.Loading())
            expenseDao.get3DayExpenses().collect { expenseList ->
                emit(Resource.Success(expenseList))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Something is wrong"))
        }
    }

    override fun get14DayExpenses(): Flow<Resource<List<Expense>>> = flow {
        try {
            emit(Resource.Loading())
            expenseDao.get14DayExpenses().collect { fourteenDayExpenseList ->
                emit(Resource.Success(fourteenDayExpenseList))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Something is wrong"))
        }
    }

    override suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }

    override suspend fun getExpenseById(id: Int): Expense? {
        return expenseDao.getExpenseById(id)
    }

    override suspend fun getExpensesForMonthRange(
        startMonth: LocalDateTime,
        endMonth: LocalDateTime
    ): List<Expense> {
        return expenseDao.getExpensesForMonthRange(startMonth, endMonth)
    }
}