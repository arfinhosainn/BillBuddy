package com.example.payments.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import kotlin.text.replace
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.home.HomeViewModel
import com.example.payments.AddEditPaymentViewModel
import com.example.ui.theme.DarkGreen
import com.example.ui.theme.LightGreen100
import com.example.ui.theme.LightRed
import com.example.billbuddy.util.FontAverta

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YourPaymentAndBudgetBox(
    homeViewModel: HomeViewModel = hiltViewModel(),
    addEditPaymentViewModel: AddEditPaymentViewModel = hiltViewModel()
) {
    val setBudget by addEditPaymentViewModel.setBudgetAmount.collectAsState()
    val amount by homeViewModel.paymentList.collectAsState()
    val total = amount.payments.sumOf { it.paymentAmount.removeCommasAndDecimals().toInt() }
    val totalAmount = total + setBudget.toInt()
    val setBudgetAmount =
        setBudget.toFloat() / totalAmount
    val paymentTotalAmount =
        total.toFloat() / totalAmount

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(LightGreen100)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 15.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "This Month",
                        style = TextStyle(
                            color = DarkGreen,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(if (setBudgetAmount > 0) setBudgetAmount else 10f) // ensure weight is at least 0.01 to prevent crash
                        .background(DarkGreen)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 15.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "To Pay",
                            style = TextStyle(
                                color = Color.White,
                                fontFamily = FontAverta,
                                fontWeight = FontWeight.Normal
                            )
                        )
                        Text(
                            text = total.toString(),
                            style = TextStyle(
                                color = Color.White,
                                fontFamily = FontAverta,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(if (paymentTotalAmount > 0) paymentTotalAmount else 10f) // ensure weight is at least 0.01 to prevent crash
                        .background(LightRed)
                )
            }
        }
    }
}

fun String.removeCommasAndDecimals(): String {
    val commaIndex = this.indexOf(",")
    val decimalIndex = this.indexOf(".")
    val endIndex = if (commaIndex >= 0) {
        commaIndex
    } else if (decimalIndex >= 0) {
        decimalIndex
    } else {
        this.length
    }
    return this.substring(0, endIndex).replace(",", "").replace(".", "")
}
