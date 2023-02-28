package com.example.billbuddy.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.billbuddy.R
import com.example.billbuddy.presentation.navigation.BottomNavBar
import com.example.billbuddy.presentation.navigation.BottomNavItem
import com.example.billbuddy.ui.theme.DarkGreen
import com.example.billbuddy.ui.theme.Heading
import com.example.billbuddy.ui.theme.LightBlack200
import com.example.billbuddy.ui.theme.LightGreen
import com.example.billbuddy.util.FontAverta

@Composable
fun HomeScreen(navController: NavController) {

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Welcome Edla!", style =
                    TextStyle(
                        fontFamily = FontAverta,
                        fontWeight = FontWeight.Bold,
                        fontSize = Heading
                    )
                )
            }, actions = {
                IconButton(
                    onClick = { /* Handle button click */ },
                    modifier = Modifier
                        .background(
                            LightGreen,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.bell),
                        contentDescription = "Favorite", tint = LightBlack200,
                        modifier = Modifier.size(22.dp)
                    )
                }

            }, elevation = 0.dp,
                backgroundColor = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        },
        floatingActionButton = {

            FloatingActionButton(
                onClick = { /*TODO*/ },
                backgroundColor = DarkGreen,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Icon"
                )

            }
        }, bottomBar = {
            BottomNavBar(
                items = listOf(
                    BottomNavItem(
                        title = "Home",
                        route = "home",
                        icon = ImageVector.vectorResource(id = R.drawable.home)
                    ),
                    BottomNavItem(
                        title = "Expenses",
                        route = "expenses",
                        icon = ImageVector.vectorResource(id = R.drawable.coins)
                    ),
                    BottomNavItem(
                        title = "Reports",
                        route = "reports",
                        icon = ImageVector.vectorResource(id = R.drawable.finance)
                    ),
                    BottomNavItem(
                        title = "Settings",
                        route = "settings",
                        icon = ImageVector.vectorResource(id = R.drawable.setting)
                    ),
                ), navController = navController, onItemClick = {}
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            val item = 1000
            LazyColumn {
                items(item) {
                    Text(text = it.toString())
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navigation = rememberNavController()
    HomeScreen(navController = navigation)

}