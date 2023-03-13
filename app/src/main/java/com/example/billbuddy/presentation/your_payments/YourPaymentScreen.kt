package com.example.billbuddy.presentation.your_payments

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billbuddy.R
import com.example.billbuddy.data.local.model.Payment
import com.example.billbuddy.data.local.model.PaymentHistory
import com.example.billbuddy.presentation.components.PaymentCardView
import com.example.billbuddy.presentation.home.HomeViewModel
import com.example.billbuddy.presentation.navigation.Screens
import com.example.billbuddy.presentation.your_payments.components.YourPaymentAndBudgetBox
import com.example.billbuddy.ui.theme.DarkGreen
import com.example.billbuddy.ui.theme.Heading
import com.example.billbuddy.ui.theme.LightBlack200
import com.example.billbuddy.ui.theme.LightGreen
import com.example.billbuddy.util.FontAverta
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun YourPaymentScreen(navController: NavController) {

    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(backgroundColor = Color.White, elevation = 0.dp,
                title = {
                    Text(
                        text = "Home", style =
                        TextStyle(
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Bold,
                            fontSize = Heading
                        )
                    )
                }, navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Home", tint = Color.Black
                        )

                    }
                }, actions = {

                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "back", tint = Color.Black
                        )

                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Your Payments", style =
                    TextStyle(
                        fontFamily = FontAverta,
                        fontWeight = FontWeight.Bold,
                        fontSize = Heading
                    )
                )

                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "back", tint = Color.Black
                    )


                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "back", tint = Color.Black
                        )

                    }
                }
            }
            YourPaymentAndBudgetBox()
            TabView(pagerState = pagerState)
            TabPager(pagerState = pagerState, navController)
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabView(
    pagerState: PagerState
) {

    val tabItem = listOf(
        "Upcoming" to R.drawable.broadband,
        "OverDue" to R.drawable.tv,
        "Paid" to R.drawable.water
    )

    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        backgroundColor = Color.White,
        contentColor = Color.DarkGray,
        divider = {
            TabRowDefaults.Divider(
                thickness = 3.dp,
                color = LightGreen
            )
        },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                color = DarkGreen
            )
        }
    ) {
        tabItem.forEachIndexed { index, pair ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }, enabled = true
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(50.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = CenterHorizontally
                ) {
                    if (pair.second != 0) {
                        Image(
                            painter = painterResource(id = pair.second),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(20.dp)
                                .clip(
                                    CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.padding(vertical = 2.dp))
                    }

                    Text(
                        text = pair.first,
                        modifier = Modifier.align(CenterHorizontally),
                        fontFamily = FontAverta,
                        fontSize = if (pagerState.currentPage == index) {
                            16.sp
                        } else {
                            12.sp
                        },
                        color = if (pagerState.currentPage == index) {
                            DarkGreen
                        } else {
                            LightBlack200
                        },
                        fontWeight = if (pagerState.currentPage == index) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        }
                    )

                }

            }
        }


    }

}


@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun TabPager(
    pagerState: PagerState, navController: NavController
) {

    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        Box(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(count = 3, state = pagerState) { pager ->
                when (pager) {
                    0 -> {
                        PaymentLazyList(navController = navController)

                    }
                    1 -> {
                        MockLazyList1()

                    }
                    2 -> {
                        MockLazyList2()
                    }
                }

            }

        }

    }
}



@Composable
fun PaymentList(
    payment: Payment,
    onClick: () -> Unit
) {
    PaymentCardView(
        paymentIcon = payment.paymentIcon,
        paymentTitle = payment.paymentTitle,
        paymentDate = payment.paymentDate.toString(),
        paymentAmount = payment.paymentAmount.toString(),
        onClick = onClick
    )

}

@Composable
fun PaymentLazyList(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {

    val paymentListState by homeViewModel.paymentList.collectAsState()
    val paymentList = paymentListState.payments



    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(paymentList) {
            PaymentList(payment = it,
                onClick = {
                    navController.navigate(Screens.AddEditPayment.route + "?paymentId=${it.id}")
                    Log.d("paymentId", "PaymentLazyList: ${it.id}")
                }
            )

        }
    }
}

@Composable
fun MockLazyList1() {
    val list = 100

    LazyColumn {
        items(list) {
            Text(text = it.toString())
        }
    }

}

@Composable
fun MockLazyList2() {
    val list = 50

    LazyColumn {
        items(list) {
            Text(text = it.toString())
        }
    }

}

