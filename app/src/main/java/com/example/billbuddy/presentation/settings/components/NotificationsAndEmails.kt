package com.example.billbuddy.presentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.billbuddy.R
import com.example.billbuddy.presentation.navigation.Screens
import com.example.billbuddy.ui.theme.LightGreen
import com.example.billbuddy.util.FontAverta

@Composable
fun NotificationsAndEmail(navController: NavController) {
    Card(
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screens.Notifications.route)
            }
            .height(60.dp),
        shape = RoundedCornerShape(0.dp),
        backgroundColor = LightGreen
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Notifications & Emails",
                fontFamily = FontAverta,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Start
            )

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Icon(
                    painter = painterResource(R.drawable.edit),
                    contentDescription = null,
                    modifier = Modifier.then(Modifier.size(16.dp))
                )
            }
        }
    }
}