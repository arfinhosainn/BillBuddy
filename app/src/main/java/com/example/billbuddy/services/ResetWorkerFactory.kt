package com.example.billbuddy.services

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.billbuddy.domain.repository.DataStoreOperation
import javax.inject.Inject

class ResetWorkerFactory @Inject constructor(
    private val dataStoreOperation: DataStoreOperation
): WorkerFactory() {


    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return BudgetLimitResetWorker(appContext, workerParameters, dataStoreOperation)
    }
}