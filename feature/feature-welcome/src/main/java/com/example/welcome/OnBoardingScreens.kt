package com.example.welcome

import androidx.annotation.DrawableRes
import com.example.feature_welcome.R

sealed class OnBoardingScreens(
    @DrawableRes
    val icon: Int,
    val title: String,
    val description: String
) {
    object FirstPage : OnBoardingScreens(
        R.drawable.onboardin1,
        "Track Bills",
        "Easily keep a track of you spending" +
            "and stay on top of your bills"
    )

    object SecondPage : OnBoardingScreens(
        R.drawable.onboarding2,
        "Manage Expenses",
        "Simply add your bills and expenses to" +
            " get a clear view of your financial " +
            "situation "
    )

    object ThirdPage : OnBoardingScreens(
        R.drawable.onboarding3,
        "Reports & Graphs",
        "See reports and visualization of your" +
            "spending habits and trends"
    )
}