package com.example.billbuddy.presentation.navigation

sealed class Screens(val route: String) {
    object AddEditPayment : Screens("add_edit_payment_screen")
    object ExpenseInsight : Screens("expense_insight_screen")
    object Authentication : Screens("authentication_screen")
    object OTPScreen : Screens("OTP_screen")
    object Reports : Screens("reports_screen")
    object Home : Screens("home_screen")
    object Currency : Screens("currency_screen")
    object Settings : Screens("settings_screen")
    object YourPayments : Screens("your_payments")
    object Welcome : Screens("welcome_screen")
    object Notifications: Screens("notifications_screen")
    object Payment: Screens("notifications_screen")
    object Splash: Screens("splash_screen")
    object Expense: Screens("expense_screen")
    object History: Screens("history_screen")
}
