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
import com.example.billbuddy.ui.theme.LightBlack
import com.example.billbuddy.ui.theme.LightGreen100
import com.example.billbuddy.util.FontAverta

@Composable
fun Logout(navController: NavController) {
    Card(elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screens.Notifications.route)
            }
            .height(60.dp), shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp),
        backgroundColor = LightBlack
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.logout),
                contentDescription = "logout",
                modifier = Modifier.then(Modifier.size(20.dp))
            )
            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = "Logout",
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