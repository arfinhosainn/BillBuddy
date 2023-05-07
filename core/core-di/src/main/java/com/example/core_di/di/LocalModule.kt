package com.example.core_di.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.billbuddy.data.local.*
import com.example.core_database.BillBuddyDatabase
import com.example.core_database.DataStoreOperationImpl
import com.example.core_database.ExpenseDao
import com.example.core_database.ExpenseRepositoryImpl
import com.example.core_database.NotificationDao
import com.example.core_database.NotificationRepositoryImpl
import com.example.core_database.PaymentDao
import com.example.core_database.PaymentHistoryDao
import com.example.core_database.PaymentHistoryRepositoryImpl
import com.example.core_database.PaymentRepositoryImpl
import com.example.core_database.TransactionDao
import com.example.core_database.TransactionRepositoryImpl
import com.example.core_domain.repository.DataStoreOperation
import com.example.core_domain.repository.ExpenseRepository
import com.example.core_domain.repository.NotificationsRepository
import com.example.core_domain.repository.PaymentHistoryRepository
import com.example.core_domain.repository.PaymentRepository
import com.example.core_domain.repository.TransactionRepository
import com.example.notification.AndroidAlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.O)
@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun providesDataStoreOperation(@ApplicationContext context: Context): DataStoreOperation {
        return DataStoreOperationImpl(context)
    }

    @Provides
    @Singleton
    fun providePaymentRepository(paymentDao: PaymentDao): PaymentRepository {
        return PaymentRepositoryImpl(paymentDao = paymentDao)
    }

    @Provides
    @Singleton
    fun providesPaymentHistoryRepository(paymentHistoryDao: PaymentHistoryDao): PaymentHistoryRepository {
        return PaymentHistoryRepositoryImpl(paymentHistoryDao)
    }

    @Provides
    @Singleton
    fun providesPaymentHistoryDao(billBuddyDatabase: BillBuddyDatabase): PaymentHistoryDao {
        return billBuddyDatabase.paymentHistoryDao
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(expenseDao: ExpenseDao): ExpenseRepository {
        return ExpenseRepositoryImpl(expenseDao)
    }

    @Provides
    @Singleton
    fun providesExpenseDao(billBuddyDatabase: BillBuddyDatabase): ExpenseDao {
        return billBuddyDatabase.expenseDao
    }

    @Provides
    fun provideAndroidAlarmScheduler(@ApplicationContext context: Context): AndroidAlarmScheduler {
        return AndroidAlarmScheduler(context)
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(transactionDao: TransactionDao): TransactionRepository {
        return TransactionRepositoryImpl(dao = transactionDao)
    }


    @Provides
    @Singleton
    fun providesBillBuddyDatabase(@ApplicationContext context: Context): BillBuddyDatabase {
        return Room.databaseBuilder(context, BillBuddyDatabase::class.java, "billbuddy_db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesPaymentDao(database: BillBuddyDatabase): PaymentDao {
        return database.paymentDao
    }

    @Provides
    @Singleton
    fun providesNotificationDao(database: BillBuddyDatabase): NotificationDao {
        return database.notificationDao
    }

    @Provides
    @Singleton
    fun providesNotificationRepository(notificationDao: NotificationDao): NotificationsRepository {
        return NotificationRepositoryImpl(notificationDao = notificationDao)
    }

    @Provides
    @Singleton
    fun providesTransactionDao(database: BillBuddyDatabase): TransactionDao {
        return database.transactionDao
    }
}