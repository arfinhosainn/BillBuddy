package com.example.billbuddy.presentation.your_payments.add_edit_payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.billbuddy.util.ExpenseIcon

@Composable
fun PaymentIconBottomSheetContent(onIconSelected: (ExpenseIcon) -> Unit) {
    val expenseIcons = listOf(
        ExpenseIcon.MOBILE,
        ExpenseIcon.WATER,
        ExpenseIcon.ELECTRICITY,
        ExpenseIcon.TV,
        ExpenseIcon.BROADBAND,
        ExpenseIcon.EDUCATION
    )

    LazyVerticalGrid(GridCells.Fixed(3)) {
        items(expenseIcons.size) { index ->
            PaymentIconItem(expenseIcon = expenseIcons[index], onIconSelected = onIconSelected)
        }
    }
}

@Composable
fun PaymentIconItem(expenseIcon: ExpenseIcon, onIconSelected: (ExpenseIcon) -> Unit) {
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
