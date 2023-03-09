package com.example.billbuddy.presentation.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

@Composable
fun PagerScreen(
    page: OnBoardingScreens
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = page.icon),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.6f)
        )
        Text(
            text = page.title,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = page.description,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.fillMaxWidth(.7f)
        )

    }
}


@ExperimentalPagerApi
@Composable
fun GetStartedButton(modifier: Modifier, pagerState: PagerState, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(.8f)
    ) {
        AnimatedVisibility(
            modifier = modifier.fillMaxWidth(),
            visible = pagerState.currentPage == 2
        ) {
            Button(
                onClick = { onClick() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
                ),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text(
                    text = "Get started",
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun FirstPagerPreview() {
    PagerScreen(page = OnBoardingScreens.FirstPage)
}

@Composable
@Preview(showBackground = true)
fun SecondPagerPreview() {
    PagerScreen(page = OnBoardingScreens.SecondPage)
}

@Composable
@Preview(showBackground = true)
fun ThirdPagerPreview() {
    PagerScreen(page = OnBoardingScreens.ThirdPage)
}