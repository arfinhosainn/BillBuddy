package com.example.billbuddy.presentation.home

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billbuddy.data.local.model.PaymentHistory
import com.example.billbuddy.presentation.components.PaymentCardView
import com.example.billbuddy.ui.theme.DarkGreen
import com.example.billbuddy.ui.theme.Heading
import com.example.billbuddy.ui.theme.Purple200
import com.example.billbuddy.util.FontAverta
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun PaymentHistoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val historyList by homeViewModel.paymentHistory.collectAsState()
    val dateListState = rememberLazyListState()
    val itemsListState = rememberLazyListState()
    var selectedSectionIndex by remember { mutableStateOf(0) }
    val scaffoldState = rememberScaffoldState()


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(backgroundColor = Color.White, elevation = 0.dp,
                title = {
                    Text(
                        text = "Home", style =
                        TextStyle(
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Bold,
                            fontSize = Heading
                        )
                    )
                }, navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back", tint = Color.Black
                        )

                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            PaymentHistoryDateRow(
                selectedIndex = selectedSectionIndex,
                dateSections = historyList,
                sectionsListState = dateListState,
                onClick = { sectionIndex ->
                    selectedSectionIndex = sectionIndex

                    scope.launch {
                        dateListState.animateScrollToItem(sectionIndex)
                        itemsListState.animateScrollToItem(sectionIndex)
                    }
                }
            )

            Divider()

            PaymentHistoryScreenList(
                paymentHistorySections = historyList,
                itemsListState = itemsListState,
                onPaymentHistoryScroll = {
                    val currentSectionIndex = itemsListState.firstVisibleItemIndex
                    if (selectedSectionIndex != currentSectionIndex) {
                        selectedSectionIndex = currentSectionIndex

                        scope.launch {
                            dateListState.animateScrollToItem(currentSectionIndex)
                        }
                    }
                }
            )
        }


    }


}


@Composable
fun PaymentHistoryScreenList(
    paymentHistorySections: List<PaymentHistory>,
    itemsListState: LazyListState,
    onPaymentHistoryScroll: () -> Unit
) {
    LazyColumn(
        state = itemsListState,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .nestedScroll(object : NestedScrollConnection {
                override fun onPostScroll(
                    consumed: Offset,
                    available: Offset,
                    source: NestedScrollSource
                ): Offset {
                    onPaymentHistoryScroll()
                    return super.onPostScroll(consumed, available, source)
                }
            })
    ) {
        paymentHistorySections.forEach { paymentHistory ->

            item {
                PaymentCardView(
                    paymentIcon = paymentHistory.paymentIcon,
                    paymentTitle = paymentHistory.paymentTitle,
                    paymentDate = paymentHistory.paymentDate.toString(),
                    paymentAmount = paymentHistory.paymentAmount
                ) {

                }
            }
        }
    }
}


@Composable
fun PaymentHistoryDateRow(
    selectedIndex: Int,
    dateSections: List<PaymentHistory>,
    sectionsListState: LazyListState,
    onClick: (sectionIndex: Int) -> Unit
) {

    LazyRow(modifier = Modifier.padding(), state = sectionsListState) {
        dateSections.forEachIndexed { index, payment ->
            item {
                val monthFormatter = DateTimeFormatter.ofPattern("MMM", Locale.getDefault())
                val monthName = payment.paymentDate.format(monthFormatter)
                DateSectionText(
                    text = monthName,
                    isSelected = selectedIndex == index,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .clickable { onClick(index) },
                )

            }
        }
    }
}


@Composable
fun DateSectionText(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean
) {
    Column(modifier) {
        var textWidth by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current

        Text(
            modifier = Modifier.onGloballyPositioned {
                textWidth =
                    with(density) { it.size.width.toDp() } //update text width value according to the content size
            },
            text = text,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontAverta,
                fontWeight = FontWeight.Bold
            ),
            color = if (isSelected) DarkGreen else Color.Black
        )

        //Show the text underline with animation
        AnimatedVisibility(
            visible = isSelected,
            enter = expandHorizontally() + fadeIn(),
            exit = shrinkHorizontally() + fadeOut()
        ) {
            Box(
                Modifier
                    .width(textWidth)
                    .padding(top = 15.dp)
                    .height(3.dp)
                    .background(Purple200)
            ) {}
        }
    }
}

