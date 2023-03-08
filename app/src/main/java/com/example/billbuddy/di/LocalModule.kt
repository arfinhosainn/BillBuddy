package com.example.billbuddy.di

import android.content.Context
import androidx.room.Room
import com.example.billbuddy.data.local.BillBuddyDatabase
import com.example.billbuddy.data.local.PaymentDao
import com.example.billbuddy.data.local.TransactionDao
import com.example.billbuddy.data.local.repository.DataStoreOperationImpl
import com.example.billbuddy.data.local.repository.PaymentRepositoryImpl
import com.example.billbuddy.data.local.repository.TransactionRepositoryImpl
import com.example.billbuddy.domain.repository.DataStoreOperation
import com.example.billbuddy.domain.repository.PaymentRepository
import com.example.billbuddy.domain.repository.TransactionRepository
import com.example.billbuddy.services.AndroidAlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
    fun providesTransactionDao(database: BillBuddyDatabase): TransactionDao {
        return database.transactionDao
    }

}