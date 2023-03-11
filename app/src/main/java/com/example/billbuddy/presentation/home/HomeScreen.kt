package com.example.billbuddy.presentation.home

import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billbuddy.R
import com.example.billbuddy.data.local.model.Payment
import com.example.billbuddy.presentation.your_payments.add_edit_payment.AddEditPaymentViewModel
import com.example.billbuddy.presentation.components.BriefPaymentItem
import com.example.billbuddy.presentation.components.CardView
import com.example.billbuddy.presentation.navigation.BottomNavBar
import com.example.billbuddy.presentation.navigation.BottomNavItem
import com.example.billbuddy.presentation.navigation.Screens
import com.example.billbuddy.presentation.your_payments.PaymentList
import com.example.billbuddy.ui.theme.DarkGreen
import com.example.billbuddy.ui.theme.Heading
import com.example.billbuddy.ui.theme.LightBlack200
import com.example.billbuddy.ui.theme.LightGreen
import com.example.billbuddy.util.FontAverta
import com.google.accompanist.pager.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    addEditPaymentViewModel: AddEditPaymentViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val cardItem by homeViewModel.paymentList.collectAsState()
    val paymentDate by addEditPaymentViewModel.paymentDate.collectAsState()
    val pagerState = rememberPagerState()
    val paymentListState by homeViewModel.paymentList.collectAsState()
    val paymentList = paymentListState.payments
    val listSize = cardItem.payments.size

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Welcome Edla!", style =
                    TextStyle(
                        fontFamily = FontAverta,
                        fontWeight = FontWeight.Bold,
                        fontSize = Heading
                    )
                )
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
                        route = "expenses",
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
                ), navController = navController, onItemClick = {}
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Welcome Edla!", style =
                            TextStyle(
                                fontFamily = FontAverta,
                                fontWeight = FontWeight.Bold,
                                fontSize = Heading
                            )
                        )
                    }
                    HorizontalPager(
                        count = listSize,
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) { page ->
                        val payment = cardItem.payments[page]
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            CardView(
                                billTitle = payment.paymentTitle,
                                billAmount = payment.paymentAmount,
                                billDate = payment.paymentDate.toString(),
                                remainingBudget = "$59",
                                billPay = "Mark Paid",
                                billPaid = "Pay Now",
                                billIcon = payment.paymentIcon
                            )
                        }
                    }
                }

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
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                    ) {
                        items(paymentList) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                            ) {
                                BriefPaymentList(payment = it,
                                    onClick = {
                                        navController.navigate(Screens.AddEditPayment.route + "?paymentId=${it.id}")
                                    }
                                )
                            }
                        }
                    }
                }

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

                items(paymentList) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        PaymentList(payment = it,
                            onClick = {
                                navController.navigate(Screens.AddEditPayment.route + "?paymentId=${it.id}")
                                Log.d("paymentId", "PaymentLazyList: ${it.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BriefPaymentList(
    payment: Payment,
    onClick: () -> Unit
) {
    BriefPaymentItem(
        paymentIcon = payment.paymentIcon,
        paymentTitle = payment.paymentTitle,
        paymentDate = payment.paymentDate.toString(),
        onClick = onClick
    )

}


