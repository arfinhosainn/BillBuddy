package com.example.home.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ui.components.BriefPaymentItem
import com.example.ui.theme.DarkGreen
import com.example.ui.theme.FontAverta
import com.example.ui.theme.LightBlack200
import com.example.util.Screens
import com.example.util.model.Payment
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YourPaymentItem(paymentListState: PaymentListState, navController: NavController) {
    var selectedSectionIndex by remember { mutableStateOf(0) }
    val dateListState = rememberLazyListState()
    val itemsListState = rememberLazyListState()

    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()) {
        YourPaymentDateRow(
            selectedIndex = selectedSectionIndex,
            dateSections = paymentListState.payments,
            sectionsListState = dateListState,
            onClick = { sectionIndex ->
                selectedSectionIndex = sectionIndex

                scope.launch {
                    dateListState.animateScrollToItem(sectionIndex)
                    itemsListState.animateScrollToItem(sectionIndex)
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        YourPaymentRow(
            paymentSections = paymentListState.payments,
            itemsListState = itemsListState,
            onPaymentScroll = {
                val currentSectionIndex = itemsListState.firstVisibleItemIndex
                if (selectedSectionIndex != currentSectionIndex) {
                    selectedSectionIndex = currentSectionIndex
                    scope.launch {
                        dateListState.animateScrollToItem(currentSectionIndex)
                    }
                }
            },
            navController = navController
        )
    }
}

@Composable
fun YourPaymentRow(
    paymentSections: List<Payment>,
    itemsListState: LazyListState,
    onPaymentScroll: () -> Unit,
    navController: NavController
) {
    LazyRow(
        state = itemsListState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .nestedScroll(object : NestedScrollConnection {
                override fun onPostScroll(
                    consumed: Offset,
                    available: Offset,
                    source: NestedScrollSource
                ): Offset {
                    onPaymentScroll()
                    return super.onPostScroll(consumed, available, source)
                }
            }),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        paymentSections.forEach { yourPayment ->
            item {
                BriefPaymentItem(
                    paymentIcon = yourPayment.paymentIcon,
                    paymentTitle = yourPayment.paymentTitle,
                    paymentDate = yourPayment.paymentDate.toString(),
                    onClick = {
                        navController.navigate(
                            Screens.AddEditPayment.route + "?paymentId=${yourPayment.id}"
                        )
                    },
                    modifier = Modifier
                        .height(70.dp)
                        .width(180.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YourPaymentDateRow(
    selectedIndex: Int,
    dateSections: List<Payment>,
    sectionsListState: LazyListState,
    onClick: (sectionIndex: Int) -> Unit
) {
    val uniqueMonths = dateSections.groupBy { it.paymentDate.month }.values.map { it.first() }

    LazyRow(modifier = Modifier.padding(), state = sectionsListState) {
        dateSections.forEachIndexed { index, payment ->
            item {
                val monthFormatter = DateTimeFormatter.ofPattern("MMM", Locale.getDefault())

                val dateText = if (payment.paymentDate.month == YearMonth.now().month) {
                    "This Month"
                } else {
                    payment.paymentDate.format(monthFormatter)
                }

                if (payment in uniqueMonths) {
                    DateSecText(
                        text = dateText,
                        isSelected = selectedIndex == index,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clickable { onClick(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun DateSecText(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean
) {
    Column(modifier = modifier.padding(start = 10.dp)) {
        var textWidth by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current

        Text(
            modifier = Modifier.onGloballyPositioned {
                textWidth =
                    with(density) { it.size.width.toDp() }
            },
            text = text,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontAverta,
                fontWeight = FontWeight.Bold
            ),
            color = if (isSelected) DarkGreen else LightBlack200
        )
    }
}