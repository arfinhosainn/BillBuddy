package com.example.billbuddy.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.billbuddy.notification_screen.NotificationsScreen
import com.example.billbuddy.presentation.add_edit_payment_screen.AddEditPaymentScreen
import com.example.billbuddy.presentation.home_screen.HomeScreen
import com.example.billbuddy.presentation.your_payments.YourPaymentScreen

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
) {

    NavHost(navController = navHostController, startDestination = Screens.Home.route) {
        composable(route = Screens.Home.route) {
            HomeScreen(navController = navHostController)
        }
        composable(route = Screens.AddEditPayment.route) {
            AddEditPaymentScreen(navController = navHostController)
        }
        composable(route = Screens.YourPayments.route) {
            YourPaymentScreen(navController = navHostController)
        }
        composable(route = Screens.Notifications.route) {
            NotificationsScreen(navController = navHostController)
        }
    }


}