package com.example.billbuddy.presentation.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.billbuddy.presentation.home.HomeViewModel

@Composable
fun TestScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    val expensesByDate = homeViewModel.monthlyTransaction.value

    val dates = expensesByDate.keys.sorted()
    Column(modifier = Modifier.fillMaxSize()) {
        LazyRow(modifier = Modifier.width(90.dp)) {
            items(dates) { date ->
                Text(
                    text = date,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
                )
                val expensesForDate = expensesByDate[date] ?: emptyList()
                LazyRow(modifier = Modifier.width(80.dp)) {
                    items(expensesForDate) { expense ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(120.dp)
                                .height(100.dp)
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = expense.paymentAmount.toString())
                        }
                    }
                }
            }
        }


    }


}