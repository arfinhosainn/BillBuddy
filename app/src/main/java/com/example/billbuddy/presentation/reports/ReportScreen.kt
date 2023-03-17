package com.example.billbuddy.presentation.reports

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
import com.example.billbuddy.ui.theme.Heading
import com.example.billbuddy.util.FontAverta
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.model.BarData
import java.time.LocalDateTime

@Composable
fun ReportScreen(
    reportsViewModel: ReportsViewModel = hiltViewModel(),
    navController: NavController
) {

    val scaffoldState = rememberScaffoldState()

    val expenseAmount by reportsViewModel.expenseAmount.collectAsState()

    val startDate = LocalDateTime.of(2023, 1, 1, 0, 0)
    val endDate = LocalDateTime.of(2023, 9, 30, 23, 59, 59)

    val selectedMonthExpense = remember { mutableStateOf(0f) }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(backgroundColor = Color.White, elevation = 0.dp,
                title = {
                    Text(
                        text = "Savings", style =
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
                            contentDescription = "back", tint = Color.Black
                        )

                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize().padding(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (expenseAmount.expense.isNotEmpty()) {
                val barData = expenseAmount.expense
                    .groupBy { it.expenseDate.month }
                    .mapValues { it.value.sumOf { expense -> expense.expenseAmount.toInt() } }
                    .map { BarData(it.key.toString(), it.value.toFloat()) }
                BarChart(

                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    barData = barData,
                    color = Color(0xFF008E59),
                    onBarClick = { barData ->
                        selectedMonthExpense.value = expenseAmount.expense.filter {
                            it.expenseDate.month.toString() == barData.xValue
                        }.sumOf { it.expenseAmount.toInt() }.toFloat()
                    }
                )
                if (selectedMonthExpense.value > 0f) {
                    Text(
                        text = "Selected Month Expenses: $${selectedMonthExpense.value}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
        LaunchedEffect(Unit) {
            reportsViewModel.getSavingsByMonth(startDate, endDate)
        }

    }
}

