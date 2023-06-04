package com.example.home.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature_settings.SettingsViewModel
import com.example.home.HomeScreen
import com.example.home.HomeViewModel
import com.example.home.PaymentHistoryViewModel
import com.example.payments.AddEditPaymentViewModel
import com.example.util.Screens


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeRoute(
    navController: NavController
){

    composable(route = Screens.Home.route) {
        val homeViewModel = hiltViewModel<HomeViewModel>()
        val paymentHistoryViewModel = hiltViewModel<PaymentHistoryViewModel>()
        val settingsViewModel = hiltViewModel<SettingsViewModel>()
        val addEditPaymentViewModel = hiltViewModel<AddEditPaymentViewModel>()

        val paymentListState by homeViewModel.paymentList.collectAsState()
        val paymentHistoryState by paymentHistoryViewModel.paymentHistory.collectAsState()
        val expenseLimitState by settingsViewModel.expenseLimit.collectAsState()

        HomeScreen(
            navController = navController,
            paymentHistoryState = paymentHistoryState,
            paymentList = paymentListState,
            settingState = expenseLimitState,
            insertPaymentHistory = paymentHistoryViewModel::onEvent,
            markPaidPaymentEvent = addEditPaymentViewModel::onEvent
        )
    }

}