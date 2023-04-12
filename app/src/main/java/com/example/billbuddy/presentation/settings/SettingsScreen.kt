package com.example.billbuddy.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billbuddy.presentation.settings.components.*
import com.example.billbuddy.ui.theme.Heading
import com.example.billbuddy.util.FontAverta

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
    val currency by settingsViewModel.currency.collectAsState()

    BottomSheetScaffold(
        sheetPeekHeight = 0.dp,
        sheetContent = {},
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 0.dp,
                title = {
                    Text(
                        text = "Settings",
                        style =
                        TextStyle(
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Bold,
                            fontSize = Heading
                        )
                    )
                }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Account(
                        paymentTitle = "Arfin Hosain",
                        paymentDate = "+8801639098488",
                        paymentAmount = ">"
                    ) {
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CurrencySetting(currency = currency.currency, navController = navController)
                    PersonalInfo(navController = navController)
                    PaymentHistory(navController = navController)
                    NotificationsAndEmail(navController = navController)
                    PrivacyAndSecurity(navController = navController)
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LockApp(navController = navController)
                    About(navController = navController)
                    HelpAndFeedback(navController = navController)
                    Logout(navController = navController)
                }
            }
        }
    }
}