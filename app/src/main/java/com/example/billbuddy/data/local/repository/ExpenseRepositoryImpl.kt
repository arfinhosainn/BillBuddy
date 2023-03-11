package com.example.billbuddy.data.local.repository

import com.example.billbuddy.data.local.ExpenseDao
import com.example.billbuddy.data.local.model.Expense
import com.example.billbuddy.data.local.model.InvalidExpenseException
import com.example.billbuddy.domain.repository.ExpenseRepository
import com.example.billbuddy.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {
    override suspend fun insertExpense(expense: Expense) {
        if (expense.expenseAmount == 0.0) {
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
}