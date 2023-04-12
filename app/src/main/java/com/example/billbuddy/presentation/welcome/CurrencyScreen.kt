package com.example.billbuddy.presentation.welcome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billbuddy.presentation.navigation.Screens
import com.example.billbuddy.ui.theme.DarkGreen
import com.example.billbuddy.ui.theme.spacing
import com.example.billbuddy.util.FontAverta
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalUnitApi
@ExperimentalFoundationApi
@Composable
fun CurrencyScreen(
    navController: NavController,
    setting: Boolean?,
    welcomeViewModel: WelcomeViewModel = hiltViewModel()
) {
    val currencies by welcomeViewModel.countryCurrencies
    var selectedCountry by remember { mutableStateOf(CurrencyModel()) }

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        sheetContent = {
            ContinueButton(
                selectedCountry,
                setting,
                navController = navController,
                welcomeViewModel = welcomeViewModel
            )
        },
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp
    ) {
        Surface(color = MaterialTheme.colors.background) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(elevation = 1.dp) {
                    Text(
                        text = "Set currency",
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.W700),
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = MaterialTheme.spacing.medium,
                                end = MaterialTheme.spacing.medium,
                                top = MaterialTheme.spacing.small
                            ),
                        textAlign = TextAlign.Start
                    )
                }

                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    currencies.forEach { (firstChar, list) ->
                        stickyHeader {
                            Surface(color = MaterialTheme.colors.background) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(
                                            start = MaterialTheme.spacing.medium,
                                            end = MaterialTheme.spacing.medium,
                                            top = MaterialTheme.spacing.medium
                                        )
                                ) {
                                    Text(
                                        text = firstChar.toString(),
                                        style = MaterialTheme.typography.subtitle1,
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Start
                                    )
                                }
                            }
                        }

                        items(list) { currency ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable {
                                        selectedCountry = if (selectedCountry != currency) {
                                            coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
                                            currency
                                        } else {
                                            coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                                            CurrencyModel()
                                        }
                                    }
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = MaterialTheme.spacing.medium,
                                        vertical = MaterialTheme.spacing.small
                                    )
                            ) {
                                TextButton(
                                    onClick = {
                                        selectedCountry = if (selectedCountry != currency) {
                                            coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
                                            currency
                                        } else {
                                            coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                                            CurrencyModel()
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = if (selectedCountry == currency) {
                                            DarkGreen
                                        } else {
                                            Color.DarkGray.copy(alpha = 0.1f)
                                        },
                                        contentColor = if (selectedCountry == currency) {
                                            contentColorFor(
                                                backgroundColor = MaterialTheme.colors.primary
                                            )
                                        } else {
                                            MaterialTheme.colors.onSurface
                                        }
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(20.dp)
                                ) {
                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    fontWeight = FontWeight.W600,
                                                    fontFamily = FontAverta,
                                                    fontSize = 14.sp
                                                )
                                            ) {
                                                append(currency.country.uppercase())
                                            }

                                            withStyle(
                                                style = SpanStyle(
                                                    color = Color.DarkGray.copy(alpha = 0.5f),
                                                    fontWeight = FontWeight.Normal,
                                                    fontFamily = FontAverta,
                                                    fontSize = 14.sp
                                                )
                                            ) {
                                                append(" (${currency.currencyCode})")
                                            }
                                        },
                                        style = MaterialTheme.typography.subtitle2,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Start
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContinueButton(
    currency: CurrencyModel,
    setting: Boolean?,
    navController: NavController,
    welcomeViewModel: WelcomeViewModel = hiltViewModel()
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = MaterialTheme.spacing.small
            )
    ) {
        Button(
            onClick = {
                welcomeViewModel.saveCurrency(currency.currencyCode)
                if (setting!!) {
                    navController.popBackStack()
                } else {
                    navController.popBackStack()
                    welcomeViewModel.saveOnBoardingState(completed = true)
                    navController.navigate(Screens.Home.route)
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = DarkGreen,
                contentColor = contentColorFor(backgroundColor = Color.White)
            ),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Text(
                text = "SET",
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }
    }
}