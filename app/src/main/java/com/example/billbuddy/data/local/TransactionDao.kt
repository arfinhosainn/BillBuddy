package com.example.billbuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.billbuddy.data.local.model.TransactionDto
import com.example.billbuddy.util.TransactionType
import kotlinx.coroutines.flow.Flow


@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionDto)


    @Query("SELECT * FROM transaction_table WHERE dateOfEntry = :entryDate")
    fun getDailyTransaction(entryDate: String): Flow<List<TransactionDto>>

    @Query("SELECT * FROM transaction_table")
    fun getAllTransaction(): Flow<List<TransactionDto>>

    @Query("DELETE FROM transaction_table")
    fun eraseTransaction()

    @Query("SELECT * FROM transaction_table WHERE dateOfEntry = date('now', 'localtime') AND transactionTypes = :transactionType")
    fun getCurrentMonthExpTransaction(transactionType: String = TransactionType.EXPENSE.title): Flow<List<TransactionDto>>

    @Query("SELECT * FROM transaction_table WHERE dateOfEntry BETWEEN date('now', '-7 day') AND date('now', 'localtime') AND transactionTypes = :transactionType")
    fun getWeeklyExpTransaction(transactionType: String = TransactionType.EXPENSE.title): Flow<List<TransactionDto>>

    @Query("SELECT * FROM transaction_table WHERE dateOfEntry BETWEEN date('now', '-1 month') AND date('now', 'localtime') AND transactionTypes = :transactionType")
    fun getMonthlyExpTransaction(transactionType: String = TransactionType.EXPENSE.title): Flow<List<TransactionDto>>

    @Query("SELECT * FROM transaction_table WHERE dateOfEntry >= date('now', '-3 day') AND dateOfEntry <date('now', 'localtime') AND transactionTypes = :transactionType")
    fun get3DayTransaction(transactionType: String): Flow<List<TransactionDto>>

    @Query("SELECT * FROM transaction_table WHERE dateOfEntry >= date('now', '-7') AND dateOfEntry < date('now', 'localtime') AND transactionTypes = :transactionType")
    fun get7DayTransaction(transactionType: String): Flow<List<TransactionDto>>


    @Query("SELECT * FROM transaction_table WHERE dateOfEntry >= date('now', '-14 day') AND dateOfEntry < date('now', 'localtime') AND transactionTypes = :transactionType")
    fun get14DayTransaction(transactionType: String): Flow<List<TransactionDto>>

    @Query("SELECT * FROM transaction_table WHERE dateOfEntry >= date('now', 'start of month') AND dateOfEntry < date('now', 'localtime') AND transactionTypes = :transactionType")
    fun getStartOfMonthTransaction(transactionType: String): Flow<List<TransactionDto>>

    @Query("SELECT * FROM transaction_table WHERE dateOfEntry >= date('now', '-1 month') AND dateOfEntry < date('now', 'localtime') AND transactionTypes = :transactionType")
    fun getLastMonthTransaction(transactionType: String): Flow<List<TransactionDto>>

    @Query("SELECT * FROM transaction_table WHERE transactionTypes = :transactionType")
    fun getTransactionByType(transactionType: String): Flow<List<TransactionDto>>

}