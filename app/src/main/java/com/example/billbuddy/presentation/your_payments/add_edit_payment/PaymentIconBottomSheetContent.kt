package com.example.billbuddy.presentation.your_payments.add_edit_payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.billbuddy.ui.theme.DarkGreen
import com.example.billbuddy.ui.theme.LightBlackUltra
import com.example.billbuddy.util.PaymentIcon
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

@Composable
fun PaymentIconBottomSheetContent(onIconSelected: (PaymentIcon) -> Unit) {
    val expenseIcons = listOf(
        PaymentIcon.MOBILE,
        PaymentIcon.WATER,
        PaymentIcon.ELECTRICITY,
        PaymentIcon.TV,
        PaymentIcon.BROADBAND,
        PaymentIcon.EDUCATION
    )




    LazyVerticalGrid(GridCells.Fixed(3)) {
        items(expenseIcons.size) { index ->
            PaymentIconItem(expenseIcon = expenseIcons[index], onIconSelected = onIconSelected)
        }
    }
}











@Composable
fun PaymentCategoryIconItem(
    onIconSelected: (PaymentIcon) -> Unit,
) {
    var isSelected by remember { mutableStateOf(false) }


    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        mainAxisAlignment = MainAxisAlignment.SpaceBetween
    ){
        PaymentIcon.values().forEach {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            isSelected = !isSelected
                            onIconSelected(it)
                        }
                        .background(if (isSelected) DarkGreen else LightBlackUltra)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = it.icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp),
                            colorFilter = if (isSelected) ColorFilter.tint(Color.White) else ColorFilter.tint(
                                Color.Black
                            )
                        )
                    }
                }
            }

        }


    }



}


@Composable
fun PaymentIconItem(expenseIcon: PaymentIcon, onIconSelected: (PaymentIcon) -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onIconSelected(expenseIcon) }
            .background(Color.Gray)
    ) {
        Image(
            painter = painterResource(id = expenseIcon.icon),
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .size(48.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentIconBottomSheetContent() {
    PaymentIconBottomSheetContent(onIconSelected = {})
}
