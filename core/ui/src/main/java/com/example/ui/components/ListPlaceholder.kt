package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.billbuddy.ui.theme.spacing
import com.example.ui.R
import com.example.ui.theme.FontAverta

@Composable
fun ListPlaceholder(
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(
                start = MaterialTheme.spacing.medium,
                top = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium
            )
            .fillMaxSize()
    ) {
        Icon(
            modifier = Modifier.size(60.dp),
            painter = painterResource(R.drawable.wallet),
            tint = MaterialTheme.colors.onBackground,
            contentDescription = "no item added"
        )

        Text(
            text = label,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontFamily = FontAverta,
            fontWeight = FontWeight.Medium
        )
    }
}