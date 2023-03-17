package com.example.billbuddy.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.billbuddy.notification_screen.NotificationsScreen
import com.example.billbuddy.presentation.expense.AddEditExpenseScreen
import com.example.billbuddy.presentation.expense.ExpenseInsightScreen
import com.example.billbuddy.presentation.home.HomeScreen
import com.example.billbuddy.presentation.home.HomeViewModel
import com.example.billbuddy.presentation.home.PaymentHistoryScreen
import com.example.billbuddy.presentation.home.PaymentHistoryViewModel
import com.example.billbuddy.presentation.reports.ReportScreen
import com.example.billbuddy.presentation.settings.SettingsScreen
import com.example.billbuddy.presentation.settings.SettingsViewModel
import com.example.billbuddy.presentation.splash.SplashScreen
import com.example.billbuddy.presentation.welcome.CurrencyScreen
import com.example.billbuddy.presentation.welcome.WelcomeScreen
import com.example.billbuddy.presentation.your_payments.YourPaymentScreen
import com.example.billbuddy.presentation.your_payments.add_edit_payment.AddEditPaymentScreen
import com.example.billbuddy.presentation.your_payments.add_edit_payment.AddEditPaymentViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalUnitApi::class, ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun NavigationGraph(
    navHostController: NavHostController,
) {

    AnimatedNavHost(
        navController = navHostController, startDestination = Screens.Splash.route,
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -300 },
                animationSpec = tween(durationMillis = 300)
            ) + fadeOut(
                animationSpec = tween(
                    300,
                    easing = FastOutSlowInEasing
                )
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -300 },
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(300))
        },
    ) {
        composable(route = Screens.Home.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val paymentHistoryViewModel = hiltViewModel<PaymentHistoryViewModel>()
            val settingsViewModel = hiltViewModel<SettingsViewModel>()
            val addEditPaymentViewModel = hiltViewModel<AddEditPaymentViewModel>()

            val paymentListState by homeViewModel.paymentList.collectAsState()
            val paymentHistoryState by paymentHistoryViewModel.paymentHistory.collectAsState()
            val expenseLimitState by settingsViewModel.expenseLimit.collectAsState()

            HomeScreen(
                navController = navHostController,
                paymentHistoryState = paymentHistoryState,
                paymentList = paymentListState,
                settingState = expenseLimitState,
                insertPaymentHistory = paymentHistoryViewModel::onEvent,
                markPaidPaymentEvent = addEditPaymentViewModel::onEvent
            )
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
        composable(route = Screens.Expense.route) {
            AddEditExpenseScreen(navController = navHostController)
        }
        composable(route = Screens.ExpenseInsight.route) {
            ExpenseInsightScreen(navController = navHostController)
        }
        composable(route = Screens.Settings.route) {
            SettingsScreen(navController = navHostController)

        }
        composable(route = Screens.History.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeOut(animationSpec = tween(300))
            }) {

            val paymentHistoryViewModel = hiltViewModel<PaymentHistoryViewModel>()
            val paymentHistoryListState by paymentHistoryViewModel.paymentHistory.collectAsState()

            PaymentHistoryScreen(
                navController = navHostController,
                paymentHistoryListState = paymentHistoryListState,
            )
        }
        composable(route = Screens.Reports.route) {
            ReportScreen(navController = navHostController)
        }
    }
}