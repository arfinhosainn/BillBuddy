package com.example.billbuddy.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billbuddy.R
import com.example.billbuddy.data.local.model.Payment
import com.example.billbuddy.data.local.model.PaymentHistory
import com.example.billbuddy.presentation.components.BriefPaymentItem
import com.example.billbuddy.presentation.components.CardView
import com.example.billbuddy.presentation.components.PaymentCardView
import com.example.billbuddy.presentation.expense.ListPlaceholder
import com.example.billbuddy.presentation.navigation.BottomNavBar
import com.example.billbuddy.presentation.navigation.BottomNavItem
import com.example.billbuddy.presentation.navigation.Screens
import com.example.billbuddy.presentation.your_payments.add_edit_payment.AddEditPaymentEvent
import com.example.billbuddy.presentation.your_payments.add_edit_payment.AddEditPaymentViewModel
import com.example.billbuddy.ui.theme.*
import com.example.billbuddy.util.FontAverta
import com.google.accompanist.pager.*
import java.time.LocalDate

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    addEditPaymentViewModel: AddEditPaymentViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val cardItem by homeViewModel.paymentList.collectAsState()
    val pagerState = rememberPagerState()
    val paymentListState by homeViewModel.paymentList.collectAsState()
    val paymentHistoryList by homeViewModel.paymentHistory.collectAsState()


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = {

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = LocalDate.now().toString(), style =
                        TextStyle(
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    )
                    Text(
                        text = "Welcome Edla!", style =
                        TextStyle(
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Bold,
                            fontSize = Heading
                        )
                    )
                }
            }, actions = {
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
                        contentDescription = "Favorite", tint = LightBlack200,
                        modifier = Modifier.size(22.dp)
                    )
                }

            }, elevation = 0.dp,
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

        }, bottomBar = {
            BottomNavBar(
                items = listOf(
                    BottomNavItem(
                        title = "Home",
                        route = "home",
                        icon = ImageVector.vectorResource(id = R.drawable.home)
                    ),
                    BottomNavItem(
                        title = "Expenses",
                        route = Screens.ExpenseInsight.route,
                        icon = ImageVector.vectorResource(id = R.drawable.coins)
                    ),
                    BottomNavItem(
                        title = "Reports",
                        route = "reports",
                        icon = ImageVector.vectorResource(id = R.drawable.finance)
                    ),
                    BottomNavItem(
                        title = "Settings",
                        route = "settings",
                        icon = ImageVector.vectorResource(id = R.drawable.setting)
                    ),
                ), navController = navController, onItemClick = {
                    navController.navigate(it.route)
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                item {
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        if (cardItem.payments.isNotEmpty()) {
                            Text(
                                text = "Household", style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = Heading,
                                    color = DarkGreen
                                )
                            )
                        } else {
                            Text(
                                text = "Household", style =
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
                    if (cardItem.payments.isEmpty()) {

                        Text(
                            text = "There is no payments to show", style =
                            TextStyle(
                                fontFamily = FontAverta,
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center
                            ), color = Color.Black, modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        // Display HorizontalPager with payments
                        HorizontalPager(
                            count = cardItem.payments.size,
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) { page ->
                            val payment = cardItem.payments.getOrNull(page)
                            if (payment != null) {
                                Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                                    CardView(
                                        billTitle = payment.paymentTitle,
                                        billAmount = payment.paymentAmount.toString(),
                                        billDate = payment.paymentDate.toString(),
                                        remainingBudget = "$59",
                                        billPay = "Mark Paid",
                                        billPaid = "Pay Now",
                                        billIcon = payment.paymentIcon,
                                        onClick = {
                                            homeViewModel.insertPaymentHistory(
                                                PaymentHistory(
                                                    paymentTitle = payment.paymentTitle,
                                                    paymentAmount = payment.paymentAmount.toDouble(),
                                                    paymentDate = payment.paymentDate,
                                                    payeeName = payment.payeeName,
                                                    paymentIcon = payment.paymentIcon
                                                )
                                            )
                                            addEditPaymentViewModel.onEvent(
                                                AddEditPaymentEvent.DeletePayment(
                                                    payment
                                                )
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }


                if (paymentListState.payments.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Your Payments", style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = Heading
                                ), modifier = Modifier.clickable {
                                    navController.navigate(Screens.Expense.route)
                                }
                            )
                            Text(
                                text = "See all", style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                ), color = DarkGreen, modifier = Modifier.clickable {
                                    navController.navigate(Screens.YourPayments.route)
                                }
                            )
                        }
                    }
                    item {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp)
                        ) {
                            items(paymentListState.payments) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                ) {
                                    YourPaymentsCard(modifier = Modifier
                                        .height(70.dp)
                                        .width(180.dp), payment = it,
                                        onClick = {
                                            navController.navigate(Screens.AddEditPayment.route + "?paymentId=${it.id}")
                                        }
                                    )
                                }
                            }
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
                                text = "Your Payments", style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = Heading
                                ), modifier = Modifier.clickable {
                                    navController.navigate(Screens.Expense.route)
                                }
                            )
                            Text(
                                text = "See all", style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                ), color = DarkGreen, modifier = Modifier.clickable {
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
                                text = "Click on + button to add new payment", style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 15.sp
                                ), color = Color.Black, modifier = Modifier.clickable {
                                    navController.navigate(Screens.YourPayments.route)
                                }
                            )
                        }
                    }
                }

                if (paymentHistoryList.isNotEmpty()) {
                    item {

                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Payments History", style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = Heading
                                )
                            )

                            Text(
                                text = "See all", style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                ), color = DarkGreen
                            )
                        }
                    }
                    items(paymentHistoryList) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            PaymentHistoryList(
                                paymentHistory = it,
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
                                text = "Payments History", style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = Heading
                                )
                            )

                            Text(
                                text = "See all", style =
                                TextStyle(
                                    fontFamily = FontAverta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                ), color = DarkGreen
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


@Composable
fun PaymentHistoryList(
    paymentHistory: PaymentHistory
) {
    PaymentCardView(
        paymentIcon = paymentHistory.paymentIcon,
        paymentTitle = paymentHistory.paymentTitle,
        paymentDate = paymentHistory.paymentDate.toString(),
        paymentAmount = paymentHistory.paymentAmount.toString()
    ) {

    }
}

@Composable
fun YourPaymentsCard(
    payment: Payment,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BriefPaymentItem(
        paymentIcon = payment.paymentIcon,
        paymentTitle = payment.paymentTitle,
        paymentDate = payment.paymentDate.toString(),
        onClick = onClick, modifier = modifier

    )

}


