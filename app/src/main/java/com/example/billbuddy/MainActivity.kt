package com.example.billbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.billbuddy.presentation.navigation.BottomNavBar
import com.example.billbuddy.presentation.navigation.BottomNavItem
import com.example.billbuddy.presentation.navigation.NavigationGraph
import com.example.billbuddy.presentation.navigation.Screens
import com.example.billbuddy.ui.theme.*
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            BillBuddyTheme {
                val nav = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val remAnimNavController = rememberAnimatedNavController()

                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomNavBar(
                            items = listOf(
                                BottomNavItem(
                                    title = "Home",
                                    route = Screens.Home.route,
                                    icon = ImageVector.vectorResource(id = R.drawable.home)
                                ),
                                BottomNavItem(
                                    title = "Expenses",
                                    route = Screens.ExpenseInsight.route,
                                    icon = ImageVector.vectorResource(id = R.drawable.coins)
                                ),
                                BottomNavItem(
                                    title = "Reports",
                                    route = Screens.Reports.route,
                                    icon = ImageVector.vectorResource(id = R.drawable.finance)
                                ),
                                BottomNavItem(
                                    title = "Settings",
                                    route = Screens.Settings.route,
                                    icon = ImageVector.vectorResource(id = R.drawable.setting)
                                )
                            ),
                            navController = remAnimNavController,
                            onItemClick = {
                                remAnimNavController.navigate(it.route)
                            }
                        )
                    }
                ) { paddingValues ->

                    Column(modifier = Modifier.padding(paddingValues)) {
                        NavigationGraph(navHostController = remAnimNavController)
                    }
                }
            }
        }
    }
}
