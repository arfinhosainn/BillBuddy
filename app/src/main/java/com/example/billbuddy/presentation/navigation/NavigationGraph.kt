package com.example.billbuddy.presentation.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.billbuddy.notification_screen.NotificationsScreen
import com.example.billbuddy.presentation.expense.AddEditExpenseScreen
import com.example.billbuddy.presentation.your_payments.add_edit_payment.AddEditPaymentScreen
import com.example.billbuddy.presentation.home.HomeScreen
import com.example.billbuddy.presentation.splash.SplashScreen
import com.example.billbuddy.presentation.welcome.CurrencyScreen
import com.example.billbuddy.presentation.welcome.WelcomeScreen
import com.example.billbuddy.presentation.your_payments.YourPaymentScreen

@OptIn(ExperimentalMaterialApi::class, ExperimentalUnitApi::class, ExperimentalFoundationApi::class)
@Composable
fun NavigationGraph(
    navHostController: NavHostController,
) {

    NavHost(navController = navHostController, startDestination = Screens.Expense.route) {
        composable(route = Screens.Home.route) {
            HomeScreen(navController = navHostController)
        }
        composable(route = Screens.AddEditPayment.route + "?paymentId={paymentId}",
            arguments = listOf(
                navArgument(
                    name = "paymentId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )) {
            AddEditPaymentScreen(navController = navHostController)
        }
        composable(route = Screens.YourPayments.route) {
            YourPaymentScreen(navController = navHostController)
        }
        composable(route = Screens.Notifications.route) {
            NotificationsScreen(navController = navHostController)
        }
        composable(route = "${Screens.Currency.route}/{setting}", arguments = listOf(
            navArgument("setting") {
                type = NavType.BoolType
                defaultValue = true
            }
        )) { entry ->
            CurrencyScreen(
                navController = navHostController,
                setting = entry.arguments?.getBoolean("setting")
            )
        }
        composable(route = Screens.Welcome.route) {
            WelcomeScreen(navController = navHostController)
        }
        composable(route = Screens.Splash.route) {
            SplashScreen(navController = navHostController)
        }

        composable(route = Screens.Expense.route){
            AddEditExpenseScreen(navController = navHostController)
        }
    }


}