package com.example.billbuddy.presentation.reports

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billbuddy.ui.theme.DarkGreen
import com.example.billbuddy.ui.theme.Heading
import com.example.billbuddy.ui.theme.LightBlack
import com.example.billbuddy.util.FontAverta
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.model.BarData
import com.example.billbuddy.presentation.components.ListPlaceholder as ListPlaceholder1

@Composable
fun ReportScreen(
    reportsViewModel: ReportsViewModel = hiltViewModel(),
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()

    val expenseAmount by reportsViewModel.startAndEndMonthYearState.collectAsState()

    val selectedMonthExpense = remember { mutableStateOf(0f) }
    val selectedBarXValue = remember { mutableStateOf("") }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 0.dp,
                title = {
                    Text(
                        text = "Savings",
                        style =
                        TextStyle(
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Bold,
                            fontSize = Heading
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Savings",
                    style =
                    TextStyle(
                        fontFamily = FontAverta,
                        fontWeight = FontWeight.Bold,
                        fontSize = Heading

                    )
                )
                Text(
                    text = "Set Goal",
                    style =
                    TextStyle(
                        fontFamily = FontAverta,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = DarkGreen
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                MonthRangeToggle()
            }

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                if (expenseAmount.expense.isNotEmpty()) {
                    val barData = expenseAmount.expense
                        .groupBy { it.expenseDate.month }
                        .mapValues { it.value.sumOf { expense -> expense.expenseAmount.toInt() } }
                        .map { BarData(it.key.toString(), it.value.toFloat()) }

                    Log.d("barData", "ReportScreen: $barData")
                    BarChart(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        barData = barData,
                        onBarClick = { barData ->
                            selectedBarXValue.value = barData.xValue as String
                            selectedMonthExpense.value = expenseAmount.expense.filter {
                                it.expenseDate.month.toString() == barData.xValue
                            }.sumOf { it.expenseAmount.toInt() }.toFloat()
                        },
                        color = if (barData.any { it.xValue == selectedBarXValue.value }) {
                            Color(
                                0xFF008E59
                            )
                        } else {
                            Color(0xFFff3c3c)
                        }
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ListPlaceholder1(label = "There is no expense reports")
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                backgroundColor = LightBlack
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Summary",
                            fontSize = 20.sp,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "All Expenses",
                            fontSize = 18.sp,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            text = "$${selectedMonthExpense.value}",
                            fontSize = 18.sp,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "All Expenses",
                            fontSize = 18.sp,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            text = "$${selectedMonthExpense.value}",
                            fontSize = 18.sp,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "All Expenses",
                            fontSize = 18.sp,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            text = "$${selectedMonthExpense.value}",
                            fontSize = 18.sp,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "All Expenses",
                            fontSize = 18.sp,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            text = "$${selectedMonthExpense.value}",
                            fontSize = 18.sp,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }
        }
    }
}
