package com.example.feature_settings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature_settings.R
import com.example.ui.theme.FontAverta
import com.example.ui.theme.LightBlack100

@Composable
fun Account(
    paymentTitle: String,
    paymentDate: String,
    paymentAmount: String,
    onClick: () -> Unit

) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp,
        backgroundColor = Color.White
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickable {
                        onClick()
                    },
                painter = painterResource(id = R.drawable.model),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = paymentTitle,
                    style = TextStyle(
                        color = Color.Black,
                        fontFamily = FontAverta,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = paymentDate,
                    style = TextStyle(
                        color = LightBlack100,
                        fontFamily = FontAverta,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(R.drawable.edit),
                contentDescription = null,
                modifier = Modifier.then(Modifier.size(16.dp))
            )
        }
    }
}