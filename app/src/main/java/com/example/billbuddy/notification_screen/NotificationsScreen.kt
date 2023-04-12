package com.example.billbuddy.notification_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billbuddy.data.local.model.Notification
import com.example.billbuddy.presentation.components.ProgressDots
import com.example.billbuddy.ui.theme.Heading
import com.example.billbuddy.ui.theme.LightBlack200
import com.example.billbuddy.util.FontAverta

@Composable
fun NotificationsScreen(
    navController: NavController,
    notificationsViewModel: NotificationsViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val notificationState = notificationsViewModel.notificationsState.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 0.dp,
                title = {
                    Text(
                        text = "Home",
                        style =
                        TextStyle(
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Bold,
                            fontSize = Heading
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Payments History",
                    style =
                    TextStyle(
                        fontFamily = FontAverta,
                        fontWeight = FontWeight.Bold,
                        fontSize = Heading
                    )
                )
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = "back",
                        tint = LightBlack200
                    )
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(notificationState.value.payments) { notification ->
                        NotificationsCard(notification = notification)
                    }
                }
                if (notificationState.value.error.isNotBlank()) {
                    Text(
                        text = notificationState.value.error,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.Center)
                    )
                }
                if (notificationState.value.isLoading) {
                    ProgressDots(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
fun NotificationsCard(notification: Notification) {
    notification.notificationDesc
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = notification.notificationTitle,
                    style = TextStyle(
                        color = Color.Black,
                        fontFamily = FontAverta,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = notification.notificationDate.toString(),
                    style = TextStyle(
                        color = LightBlack200,
                        fontFamily = FontAverta,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Text(
                text = notification.notificationDesc,
                style = TextStyle(
                    color = Color.Black,
                    fontFamily = FontAverta,
                    fontWeight = FontWeight.Medium
                )
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            )
        }
    }
}