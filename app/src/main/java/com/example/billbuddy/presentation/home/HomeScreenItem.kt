package com.example.billbuddy.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billbuddy.presentation.your_payments.add_edit_payment.AddEditPaymentViewModel
import com.example.billbuddy.presentation.components.CardView
import com.example.billbuddy.presentation.your_payments.PaymentLazyList
import java.time.LocalDate

@Composable
fun HomeScreenItem(
    addEditPaymentViewModel: AddEditPaymentViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(), navController: NavController
) {





    Column(modifier = Modifier.fillMaxSize()) {



        PaymentLazyList(navController = navController)

    }


}