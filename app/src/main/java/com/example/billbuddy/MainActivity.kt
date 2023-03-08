package com.example.billbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.billbuddy.presentation.navigation.NavigationGraph
import com.example.billbuddy.ui.theme.BillBuddyTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BillBuddyTheme {
//                TestScreen()
                val remNavController = rememberNavController()
                NavigationGraph(navHostController = remNavController)

            }
        }
    }
}

