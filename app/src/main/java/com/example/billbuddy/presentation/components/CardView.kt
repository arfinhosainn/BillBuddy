package com.example.billbuddy.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billbuddy.ui.theme.*
import com.example.billbuddy.util.FontAverta

@Composable
fun CardView(
    billTitle: String,
    billAmount: Double,
    billDate: String,
    remainingBudget: String,
    billPay: String,
    billPaid: String,
    billIcon: Int,
    modifier: Modifier = Modifier

) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(185.dp),
        backgroundColor = LightGreen,
        shape = RoundedCornerShape(15.dp), elevation = 0.dp
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
                    text = billTitle, style = TextStyle(
                        color = Color.Black,
                        fontFamily = FontAverta,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

                Text(
                    text = billDate, style = TextStyle(
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
                    onClick = { /*TODO*/ }, modifier = modifier
                        .weight(4f)
                        .height(45.dp), colors = ButtonDefaults.buttonColors(
                        backgroundColor = LightGreen100
                    )
                ) {
                    Text(
                        text = billPaid, style = TextStyle(
                            color = DarkGreen,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontAverta,
                        )
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))

                Button(
                    onClick = { /*TODO*/ }, modifier = modifier
                        .weight(4f)
                        .height(45.dp), colors = ButtonDefaults.buttonColors(
                        backgroundColor = DarkGreen
                    )
                ) {
                    Text(
                        text = billPay, style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontAverta,
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
                    text = remainingBudget, style = TextStyle(
                        color = LightBlack200,
                        fontFamily = FontAverta,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun PreviewCardView() {
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
//}