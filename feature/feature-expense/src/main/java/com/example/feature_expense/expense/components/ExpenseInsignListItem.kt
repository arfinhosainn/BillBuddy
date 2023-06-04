package com.example.feature_expense.expense.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billbuddy.ui.theme.BILL_AMOUNT
import com.example.ui.theme.DarkGreen
import com.example.ui.theme.FontAverta
import com.example.ui.theme.LightBlack100
import com.example.ui.theme.LightGreen
import com.example.util.model.Expense

@Composable
fun ExpenseInsightListItem(
    expenseIcon: Int,
    expenseTitle: String,
    expenseDate: String,
    expenseAmount: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit

) {
    Card(
        modifier = modifier
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp,
        backgroundColor = LightGreen
    ) {
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Image(
                painter = painterResource(id = expenseIcon),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .align(CenterVertically),
                colorFilter = ColorFilter.tint(DarkGreen)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = expenseTitle,
                    style = TextStyle(
                        color = Color.Black,
                        fontFamily = FontAverta,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = expenseDate,
                    style = TextStyle(
                        color = LightBlack100,
                        fontFamily = FontAverta,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = expenseAmount,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = BILL_AMOUNT,
                    fontFamily = FontAverta,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White)
    ) {
        ExpenseInsightListItem(
            expenseIcon = expense.expenseCategory.toInt(),
            expenseTitle = expense.expenseTitle,
            expenseDate = expense.expenseDate.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            expenseAmount = expense.expenseAmount.toString()
        ) {
        }
    }
}