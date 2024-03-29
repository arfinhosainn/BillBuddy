package com.example.billbuddy.presentation.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billbuddy.ui.theme.BILL_AMOUNT
import com.example.ui.theme.DarkGreen
import com.example.ui.theme.FontAverta
import com.example.ui.theme.LightBlack100
import com.example.ui.theme.LightBlack200
import com.example.ui.theme.LightGreen
import com.example.ui.theme.LightGreen100

@Composable
fun CardView(
    billTitle: String,
    billAmount: String,
    billDate: String,
    remainingBudget: String,
    billPay: String,
    billPaid: String,
    billIcon: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit

) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(185.dp),
        backgroundColor = LightGreen,
        shape = RoundedCornerShape(15.dp),
        elevation = 0.dp
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id = billIcon),
                    modifier = modifier.size(21.dp),
                    contentDescription = "bill_icon",
                    tint = LightBlack100
                )
                Text(
                    text = billAmount.toString(),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = BILL_AMOUNT,
                        fontFamily = FontAverta,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = billTitle,
                    style = TextStyle(
                        color = Color.Black,
                        fontFamily = FontAverta,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

                Text(
                    text = billDate,
                    style = TextStyle(
                        color = LightBlack100,
                        fontSize = 15.sp,
                        fontFamily = FontAverta,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        // TODO:
                    },
                    modifier = modifier
                        .weight(4f)
                        .height(45.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = LightGreen100
                    )
                ) {
                    Text(
                        text = billPaid,
                        style = TextStyle(
                            color = DarkGreen,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontAverta
                        )
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))

                Button(
                    onClick = {
                        onClick()
                    },
                    modifier = modifier
                        .weight(4f)
                        .height(45.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = DarkGreen
                    )
                ) {
                    Text(
                        text = billPay,
                        style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontAverta
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = remainingBudget,
                    style = TextStyle(
                        color = LightBlack200,
                        fontFamily = FontAverta,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

// @Preview(showBackground = true)
// @Composable
// fun PreviewCardView() {
//    CardView(
//        billTitle = "Electricity Bill",
//        billAmount = " ${987.00}".toDouble(),
//        billDate = "Due Today",
//        remainingBudget = "Remaining Budget $20000",
//        billPay = "Pay Now",
//        billPaid = "Mark Paid",
//        billIcon = ImageVector.vectorResource(id = R.drawable.coins)
//    )
//
//
// }