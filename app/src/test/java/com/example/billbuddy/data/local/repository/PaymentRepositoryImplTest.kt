package com.example.billbuddy.data.local.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.billbuddy.data.local.PaymentDao
import com.example.billbuddy.data.local.model.InvalidPaymentException
import com.example.billbuddy.data.local.model.Payment
import com.example.billbuddy.domain.repository.PaymentRepository
import com.example.billbuddy.util.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDate

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class PaymentRepositoryImplTest {

    @MockK
    lateinit var paymentDao: PaymentDao

    private lateinit var paymentRepository: PaymentRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        paymentRepository = PaymentRepositoryImpl(paymentDao)
    }

    @Test
    fun `insert payments with invalid title should throw InvalidPaymentException`() = runTest(
        UnconfinedTestDispatcher()
    ) {
        val invalidPayment = Payment(
            paymentTitle = "",
            paymentAmount = "100",
            payeeName = "Arfin",
            paymentIcon = 123,
            paymentDate = LocalDate.now()
        )
        try {
            paymentRepository.insertPayments(invalidPayment)
            assert(false) { "Expected InvalidPaymentException was not thrown" }
        } catch (e: InvalidPaymentException) {
            assert(e.message == "The title of the payment can't be empty") {
                "Invalid exception message: ${e.message}"
            }
        }
    }

    @Test
    fun `insert payments with invalid amount should throw InvalidPaymentException`() = runTest (
        UnconfinedTestDispatcher()){
        val invalidPayment = Payment(
            paymentTitle = "House Rent",
            paymentAmount = "",
            payeeName = "Arfin",
            paymentIcon = 123,
            paymentDate = LocalDate.now()
        )
        try {
            paymentRepository.insertPayments(invalidPayment)

            fail("Expected InvalidPaymentException was not thrown")

        } catch (e: InvalidPaymentException) {
            assertThat(e.message).isEqualTo("The amount of the payment can't be empty")
        }
    }


    @Test
    fun `getAllPayments should emit loading and success state with correct data`() = runBlocking {
        val payments = listOf(
            Payment(
                paymentTitle = "House Rent",
                paymentAmount = "",
                payeeName = "Arfin",
                paymentIcon = 123,
                paymentDate = LocalDate.now()
            ),
            Payment(
                paymentTitle = "Electric Bill",
                paymentAmount = "29",
                payeeName = "Arfin",
                paymentIcon = 123,
                paymentDate = LocalDate.now()
            ),
            Payment(
                paymentTitle = "Water Bill",
                paymentAmount = "324",
                payeeName = "Arfin",
                paymentIcon = 123,
                paymentDate = LocalDate.now()
            )
        )

        every { paymentDao.getAllPayments() } returns flowOf(payments)

        val result = paymentRepository.getAllPayments().toList()
        assertThat(result.size).isEqualTo(2)
        assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(result[1]).isInstanceOf(Resource.Success::class.java)
        assertThat((result[1] as Resource.Success<List<Payment>>).data).isEqualTo(payments)

    }


    @Test
    fun `getAllPayments should emit error state when IOException is thrown()`() = runBlocking {

        every { paymentDao.getAllPayments() } throws IOException("Error")

        val result = paymentRepository.getAllPayments().toList()

        assertThat(result.size).isEqualTo(2)
        assertThat(result[1]).isInstanceOf(Resource.Error::class.java)
        assertThat((result[1] as Resource.Error).message).isEqualTo("Couldn't reach server. Check your internet connection")
    }


    @Test
    fun `getAllPayments should emit error when another exception is thrown`() = runBlocking {
        every { paymentDao.getAllPayments() } throws Exception("Something is wrong")

        val result = paymentRepository.getAllPayments().toList()

        assertThat(result.size).isEqualTo(2)
        assertThat(result[1]).isInstanceOf(Resource.Error::class.java)
        assertThat((result[1] as Resource.Error).message).isEqualTo("Something is wrong")
    }


    @Test
    fun `getPaymentById should return the correct payment with his id`() = runTest (
        UnconfinedTestDispatcher()){

        val payment = Payment(
            paymentTitle = "Water Bill",
            paymentAmount = "324",
            payeeName = "Arfin",
            paymentIcon = 123,
            paymentDate = LocalDate.now()
        )
        val id = 1

        coEvery { paymentDao.getPaymentById(id) } returns payment

        val result = paymentRepository.getPaymentById(id)

        assertThat(payment).isEqualTo(result)
    }


    @Test
    fun `getPaymentById should return null when the payment database is empty`() = runTest (
        UnconfinedTestDispatcher()){

        coEvery { paymentRepository.getPaymentById(any()) } returns null

        val result = paymentRepository.getPaymentById(1)

        assertThat(result).isNull()
    }

    @Test
    fun `deletePayment should delete the payment from database`() = runTest (
        UnconfinedTestDispatcher()){
        val payment = Payment(
            paymentTitle = "Water Bill",
            paymentAmount = "324",
            payeeName = "Arfin",
            paymentIcon = 123,
            paymentDate = LocalDate.now()
        )

        coEvery { paymentDao.deletePayment(any()) } just runs

        paymentRepository.deletePayment(payments = payment)
        coVerify { paymentDao.deletePayment(payment) }
    }

}