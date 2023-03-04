package com.example.billbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.billbuddy.presentation.add_edit_payment_screen.AddEditPaymentScreen
import com.example.billbuddy.ui.theme.BillBuddyTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BillBuddyTheme {

                val remNavController = rememberNavController()
                AddEditPaymentScreen(navController = remNavController)

            }
        }
    }
}

