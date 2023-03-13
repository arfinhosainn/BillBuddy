package com.example.billbuddy.services

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.billbuddy.domain.repository.DataStoreOperation

class BudgetLimitResetWorker(
    context: Context,
    workParams: WorkerParameters,
    val dataStoreOperation: DataStoreOperation
) : CoroutineWorker(context, workParams) {
    override suspend fun doWork(): Result {
        dataStoreOperation.writeBudgetLimitToDataStore(0.0)
        return Result.success()
    }
}
