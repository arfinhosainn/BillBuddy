package com.example.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.payments.AddEditPaymentViewModel
import com.example.payments.PaymentLazyList

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