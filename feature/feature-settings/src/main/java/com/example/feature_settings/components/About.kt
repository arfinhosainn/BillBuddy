package com.example.feature_settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.feature_settings.R
import com.example.ui.theme.FontAverta
import com.example.ui.theme.LightBlack
import com.example.util.Screens

@Composable
fun About(navController: NavController) {
    Card(
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screens.Notifications.route)
            }
            .height(60.dp),
        shape = RoundedCornerShape(0.dp),
        backgroundColor = LightBlack
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.info),
                contentDescription = "about",
                modifier = Modifier.then(Modifier.size(20.dp))
            )
            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = "About",
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
