package com.example.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.billbuddy.presentation.components.CardView
import com.example.billbuddy.presentation.components.PaymentCardView
import com.example.billbuddy.ui.theme.*
import com.example.feature_settings.SettingState
import com.example.home.components.PaymentHistoryEvent
import com.example.home.components.PaymentHistoryState
import com.example.home.components.PaymentListState
import com.example.home.components.YourPaymentItem
import com.example.payments.AddEditPaymentEvent
import com.example.ui.components.ListPlaceholder
import com.example.ui.theme.DarkGreen
import com.example.ui.theme.FontAverta
import com.example.ui.theme.LightBlack200
import com.example.ui.theme.LightGreen
import com.example.ui.theme.LightGreen100
import com.example.util.Screens
import com.example.util.model.PaymentHistory
import com.google.accompanist.pager.*
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    paymentHistoryState: PaymentHistoryState,
    paymentList: PaymentListState,
    settingState: SettingState,
    insertPaymentHistory: (PaymentHistoryEvent) -> Unit,
    markPaidPaymentEvent: (AddEditPaymentEvent) -> Unit
) {
    val pagerState = rememberPagerState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = LocalDate.now().toString(),
                            style =
                            TextStyle(
                                fontFamily = FontAverta,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                        )
                        Text(
                            text = "Welcome Edla!",
                            style =
                            TextStyle(
                                fontFamily = FontAverta,
                                fontWeight = FontWeight.Bold,
                                fontSize = Heading
                            )
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(Screens.Notifications.route) },
                        modifier = Modifier
                            .background(
                                LightGreen,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.bell),
                            contentDescription = "Favorite",
                            tint = LightBlack200,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                },
                elevation = 0.dp,
                backgroundColor = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screens.AddEditPayment.route) },
                backgroundColor = DarkGreen,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Icon"
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        if (paymentList.payments.isNotEmpty()) {
                            Text(
                                text = "Household",
                                style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = Heading,
                                    color = DarkGreen
                                )
                            )
                        } else {
                            Text(
                                text = "Household",
                                style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = Heading,
                                    color = DarkGreen
                                )
                            )
                        }
                    }
                }
                item {
                    Divider(color = LightGreen100)
                }
                item {
                    if (paymentList.payments.isEmpty()) {
                        Text(
                            text = "There is no payments to show",
                            style =
                            TextStyle(
                                fontFamily = FontAverta,
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center
                            ),
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        HorizontalPager(
                            count = paymentList.payments.size,
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) { page ->
                            val payments = paymentList.payments.getOrNull(page)
                            if (payments != null) {
                                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                    CardView(
                                        billTitle = payments.paymentTitle,
                                        billAmount = payments.paymentAmount,
                                        billDate = payments.paymentDate.toString(),
                                        remainingBudget = "Remaining budget:${settingState.expenseLimit}",
                                        billPay = "Mark Paid",
                                        billPaid = "Pay Now",
                                        billIcon = payments.paymentIcon,
                                        onClick = {
                                            insertPaymentHistory(
                                                PaymentHistoryEvent.InsertPaymentHistory(
                                                    PaymentHistory(
                                                        paymentTitle = payments.paymentTitle,
                                                        paymentDate = payments.paymentDate,
                                                        paymentAmount = payments.paymentAmount,
                                                        paymentIcon = payments.paymentIcon,
                                                        payeeName = payments.payeeName
                                                    )
                                                )
                                            )
                                            markPaidPaymentEvent(
                                                AddEditPaymentEvent.DeletePayment(
                                                    payments
                                                )
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                if (paymentList.payments.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Your Payments",
                                style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = Heading
                                ),
                                modifier = Modifier.clickable {
                                    navController.navigate(Screens.Expense.route)
                                }
                            )
                            Text(
                                text = "See all",
                                style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                ),
                                color = DarkGreen,
                                modifier = Modifier.clickable {
                                    navController.navigate(Screens.YourPayments.route)
                                }
                            )
                        }
                    }
                    item {
                        YourPaymentItem(
                            paymentListState = paymentList,
                            navController = navController
                        )
                    }
                } else {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Your Payments",
                                style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = Heading
                                ),
                                modifier = Modifier.clickable {
                                    navController.navigate(Screens.Expense.route)
                                }
                            )
                            Text(
                                text = "See all",
                                style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                ),
                                color = DarkGreen,
                                modifier = Modifier.clickable {
                                    navController.navigate(Screens.YourPayments.route)
                                }
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 15.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Click on + button to add new payment",
                                style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 15.sp
                                ),
                                color = Color.Black,
                                modifier = Modifier.clickable {
                                    navController.navigate(Screens.YourPayments.route)
                                }
                            )
                        }
                    }
                }

                if (paymentHistoryState.paymentHistory.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Payments History",
                                style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = Heading
                                )
                            )
                            Text(
                                modifier = Modifier.clickable {
                                    navController.navigate(Screens.History.route)
                                },
                                text = "See all",
                                style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                ),
                                color = DarkGreen
                            )
                        }
                    }
                    items(paymentHistoryState.paymentHistory) { paymentHistory ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            PaymentCardView(
                                paymentIcon = paymentHistory.paymentIcon,
                                paymentTitle = paymentHistory.paymentTitle,
                                paymentDate = paymentHistory.paymentDate.toString(),
                                paymentAmount = paymentHistory.paymentAmount,
                                onClick = {
                                }
                            )
                        }
                    }
                } else {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Payments History",
                                style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = Heading
                                )
                            )
                            Text(
                                text = "See all",
                                style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                ),
                                color = DarkGreen
                            )
                        }
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ListPlaceholder(label = "There is no payment history")
                        }
                    }
                }
            }
        }
    }
}