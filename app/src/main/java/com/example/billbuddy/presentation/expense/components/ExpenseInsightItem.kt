package com.example.billbuddy.presentation.expense.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.billbuddy.ui.theme.spacing
import com.example.billbuddy.util.ExpenseCategoryIcon

@Composable
fun ExpenseInsightItem(
    cat: ExpenseCategoryIcon, currencyCode: String, amount: Double, percent: Float
) {
    Card(
        elevation = 0.dp,
        backgroundColor = Color.DarkGray.copy(alpha = 0.1f),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(vertical = MaterialTheme.spacing.small)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
        ) {
            Icon(
                painter = painterResource(id = cat.icon),
                contentDescription = null,
                modifier = Modifier
                    .background(cat.bgRes, shape = CircleShape)
                    .padding(16.dp),
                tint = cat.bgRes
            )

            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))


            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
            ) {
                Text(
                    text = cat.title,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = currencyCode + "$amount",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.W600),
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Start
                )
            }

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = "${String.format("%.2f", percent)}%",
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.End
                )
            }

        }
    }

}