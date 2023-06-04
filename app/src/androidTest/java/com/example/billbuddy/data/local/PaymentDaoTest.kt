package com.example.billbuddy.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.billbuddy.data.local.model.Payment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class PaymentDaoTest {

    lateinit var paymentDao: PaymentDao
    lateinit var billBuddyDatabase: BillBuddyDatabase

    @Before
    fun setUp() {
        billBuddyDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BillBuddyDatabase::class.java
        ).allowMainThreadQueries().build()
        paymentDao = billBuddyDatabase.paymentDao
    }

    @Test
    fun expecting_single_payment_insertion() = runBlocking {
        val payment = Payment(
            paymentTitle = "Bill Buddy",
            paymentAmount = "100",
            payeeName = "Arfin",
            paymentIcon = 123,
            paymentDate = LocalDate.now(),
            id = 1
        )
        paymentDao.insertPayments(payment)

        val result = paymentDao.getAllPayments().first().first().paymentTitle

        Assert.assertEquals("Bill Buddy", result)
    }

    @After
    fun tearDown() {
        billBuddyDatabase.close()
    }
}