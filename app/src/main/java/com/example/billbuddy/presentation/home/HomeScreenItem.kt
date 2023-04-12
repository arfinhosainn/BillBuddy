package com.example.billbuddy.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billbuddy.presentation.yourpayments.writepayments.AddEditPaymentViewModel
import com.example.billbuddy.presentation.yourpayments.PaymentLazyList

@Composable
fun HomeScreenItem(
    addEditPaymentViewModel: AddEditPaymentViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        PaymentLazyList(navController = navController)
    }
}