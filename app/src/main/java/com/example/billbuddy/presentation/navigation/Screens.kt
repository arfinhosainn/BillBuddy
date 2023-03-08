package com.example.billbuddy.presentation.navigation

sealed class Screens(val route: String) {
    object AddEditPayment : Screens("add_edit_payment_screen")
    object Home : Screens("home_screen")
    object YourPayments : Screens("your_payments")
}
