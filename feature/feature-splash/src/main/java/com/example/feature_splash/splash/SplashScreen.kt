package com.example.feature_splash.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import com.example.feature_splash.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ui.theme.DarkGreen
import com.example.util.Screens

@Composable
fun SplashScreen(
    splashScreenViewModel: SplashScreenViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val onBoardingCompleted by splashScreenViewModel.onBoardingCompleted.collectAsState()

    val degrees = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        degrees.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 200
            )
        )
        navController.popBackStack()
        if (onBoardingCompleted) {
            navController.navigate(Screens.Home.route)
        } else {
            navController.navigate(Screens.Welcome.route)
        }
    }
    Splash(scale = degrees.value)
}

@Composable
fun Splash(scale: Float) {
    if (isSystemInDarkTheme()) {
        Box(
            modifier = Modifier
                .background(DarkGreen)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .rotate(scale)
                    .size(250.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Splash Screen"
            )
        }
    } else {
        Box(
            modifier = Modifier
                .background(DarkGreen)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .rotate(scale)
                    .size(250.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Splash Screen"
            )
        }
    }
}
