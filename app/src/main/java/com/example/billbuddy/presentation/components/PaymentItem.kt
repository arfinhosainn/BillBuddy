package com.example.billbuddy.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.example.billbuddy.ui.theme.LightBlack
import com.example.billbuddy.ui.theme.LightBlack100
import com.example.billbuddy.ui.theme.LightBlack200
import com.example.billbuddy.util.FontAverta

@Composable
fun PaymentCardView(
    paymentIcon: Int,
    paymentTitle: String,
    paymentDate: String,
    paymentAmount: String,
    onClick: () -> Unit

) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp, backgroundColor = LightBlack
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Image(
                painter = painterResource(id = paymentIcon),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .align(CenterVertically),
                colorFilter = ColorFilter.tint(LightBlack200)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = paymentTitle, style = TextStyle(
                        color = Color.Black,
                        fontFamily = FontAverta,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = paymentDate, style = TextStyle(
                        color = LightBlack100,
                        fontFamily = FontAverta,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = paymentAmount.toString(),
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