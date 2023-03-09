package com.example.billbuddy.presentation.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billbuddy.presentation.navigation.Screens
import com.example.billbuddy.ui.theme.DarkGreen
import com.example.billbuddy.ui.theme.LightGreen100
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomeScreen(
    navController: NavController,
    welcomeViewModel: WelcomeViewModel = hiltViewModel()
) {

    val pages by welcomeViewModel.listOfPages
    val pagerState = rememberPagerState()

    Surface(color = Color.White) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                count = pages.size,
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(10f)
            ) { pageCount ->
                PagerScreen(page = pages[pageCount])
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .weight(1f),
                indicatorWidth = 18.dp,
                indicatorHeight = 4.dp,
                activeColor = DarkGreen,
                inactiveColor = LightGreen100
            )
            GetStartedButton(modifier = Modifier.weight(2f), pagerState = pagerState) {
                navController.popBackStack()
                welcomeViewModel.saveOnBoardingState(completed = true)
                navController.navigate("${Screens.Currency.route}/${false}")
            }

        }
    }


}