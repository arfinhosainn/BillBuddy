package com.example.billbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.example.billbuddy.presentation.navigation.NavigationGraph
import com.example.billbuddy.ui.theme.BillBuddyTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BillBuddyTheme {

                val remAnimNavController = rememberAnimatedNavController()
                NavigationGraph(navHostController = remAnimNavController)

            }
        }
    }
}

