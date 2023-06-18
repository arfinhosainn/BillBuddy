package com.example.feature_expense.expense.components

import android.media.tv.TvContract.PreviewPrograms
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ExpenseItems(
    color: Color
) {

    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(15.dp)
                .background(color)
        )
        Text(text = "Grocery")
    }
}

