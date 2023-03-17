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
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.billbuddy.R
import com.example.billbuddy.data.local.model.PaymentHistory
import com.example.billbuddy.presentation.components.PaymentCardView
import com.example.billbuddy.presentation.home.components.PaymentHistoryState
import com.example.billbuddy.ui.theme.DarkGreen
import com.example.billbuddy.ui.theme.Heading
import com.example.billbuddy.ui.theme.LightBlack200
import com.example.billbuddy.util.FontAverta
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun PaymentHistoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    paymentHistoryListState: PaymentHistoryState
) {
    val scope = rememberCoroutineScope()
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Payments History", style =
                    TextStyle(
                        fontFamily = FontAverta,
                        fontWeight = FontWeight.Bold,
                        fontSize = Heading
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(5f),
                    horizontalArrangement = Arrangement.End
                ) {

                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            modifier = modifier.size(28.dp),
                            painter = painterResource(id = R.drawable.filter),
                            contentDescription = "back", tint = LightBlack200
                        )

                    }
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            modifier = modifier.size(28.dp),
                            imageVector = Icons.Default.Search,
                            contentDescription = "back", tint = LightBlack200
                        )

                    }

                }


            }
            Spacer(modifier = Modifier.height(8.dp))

            PaymentHistoryDateRow(
                selectedIndex = selectedSectionIndex,
                dateSections = paymentHistoryListState.paymentHistory,
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
            Spacer(modifier = Modifier.height(8.dp))

            PaymentHistoryScreenList(
                paymentHistorySections = paymentHistoryListState.paymentHistory,
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

    val uniqueMonths = dateSections.groupBy { it.paymentDate.month }.values.map { it.first() }


    LazyRow(modifier = Modifier.padding(), state = sectionsListState) {
        dateSections.forEachIndexed { index, payment ->
            item {
                val monthFormatter = DateTimeFormatter.ofPattern("MMM", Locale.getDefault())

                val dateText = if (payment.paymentDate.month == YearMonth.now().month) {
                    "This Month ${payment.paymentDate.dayOfMonth}"
                } else {
                    payment.paymentDate.format(monthFormatter)
                }

                if (payment in uniqueMonths) {
                    DateSectionText(
                        text = dateText,
                        isSelected = selectedIndex == index,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clickable { onClick(index) },
                    )
                }
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
                fontSize = 16.sp,
                fontFamily = FontAverta,
                fontWeight = FontWeight.Medium
            ),
            color = if (isSelected) DarkGreen else LightBlack200
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
                    .background(DarkGreen)
            ) {}
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPaymentHistoryScreen() {

    val navController = rememberNavController()
    val paymentHistoryListState = PaymentHistoryState()

    PaymentHistoryScreen(
        navController = navController,
        paymentHistoryListState = paymentHistoryListState
    )

}