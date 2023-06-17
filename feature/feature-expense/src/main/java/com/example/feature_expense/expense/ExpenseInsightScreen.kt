package com.example.feature_expense.expense

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billbuddy.ui.theme.Heading
import com.example.billbuddy.ui.theme.spacing
import com.example.feature_expense.R
import com.example.feature_expense.expense.components.ExpenseItem
import com.example.feature_expense.expense.components.ExpenseItems
import com.example.feature_expense.expense.components.PieChart
import com.example.feature_settings.components.SetBudgetContent
import com.example.ui.components.ListPlaceholder
import com.example.ui.theme.DarkGreen
import com.example.ui.theme.FontAverta
import com.example.ui.theme.LightBlack
import com.example.util.ExpenseCategoryIcon
import com.example.util.Screens
import com.example.util.removeCommasAndDecimals
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterialApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun ExpenseInsightScreen(
    expenseInsightViewModel: ExpenseInsightViewModel = hiltViewModel(),
    navController: NavController
) {
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
    val expenseList by expenseInsightViewModel.fakeList.collectAsState()
    val expenseListByDate = expenseList.payments.groupBy { it.expenseDate.toLocalDate() }

    val filteredExpenses by expenseInsightViewModel.filteredExpense.collectAsState()

    val total = filteredExpenses.sumOf { it.expenseAmount.removeCommasAndDecimals().toDouble() }

    val groupData = filteredExpenses.groupBy { it.expenseCategory }

    val filteredCategories = mutableListOf<ExpenseCategoryIcon>()

    groupData.forEach { data ->
        val category = ExpenseCategoryIcon.values().find { it.icon.toString() == data.key }
        category?.let { filteredCategories.add(it) }
    }

    val amountList = groupData.map {
        it.value.sumOf { exp ->
            exp.expenseAmount.toDouble()
        }
    }

    val totalExp = amountList.map { it.toFloat() }.sum()
    val percentProgress = amountList.map {
        it.toFloat() * 100 / totalExp
    }

    var expandedState by remember {
        mutableStateOf(false)
    }

    val limitDuration by remember {
        mutableStateOf(
            listOf(
                "Last 3 Days",
                "This Week",
                "Last 14 Days",
                "This Month",
                "All"
            )
        )
    }
    var selectedDuration by remember {
        mutableStateOf(limitDuration.last())
    }
    BottomSheetScaffold(
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                TopAppBar(
                    backgroundColor = Color.White,
                    elevation = 0.dp,
                    title = {
                        Text(
                            text = "Expenses",
                            style =
                            TextStyle(
                                fontFamily = FontAverta,
                                fontWeight = FontWeight.Bold,
                                fontSize = Heading

                            )
                        )
                    },
                    actions = {
                        Text(
                            text = "Set Budget",
                            style =
                            TextStyle(
                                fontFamily = FontAverta,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = DarkGreen
                            ),
                            modifier = Modifier.clickable {
                                scope.launch {
                                    sheetState.expand()
                                }
                            }
                        )
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = 60.dp),
                onClick = { navController.navigate(Screens.Expense.route) },
                backgroundColor = DarkGreen,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Icon"
                )
            }
        },
        sheetContent = {
            SetBudgetContent(bottomSheetState = sheetState, scope = scope)
        }
    ) { paddingValues ->

        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.padding(
                start = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium,
                top = MaterialTheme.spacing.small
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .clickable {
                            expandedState = !expandedState
                        }
                        .padding(MaterialTheme.spacing.medium),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedDuration,
                        style = MaterialTheme.typography.subtitle1
                    )

                    Icon(
                        painter = painterResource(R.drawable.pop_up),
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSurface
                    )
                    DropdownMenu(
                        expanded = expandedState,
                        onDismissRequest = { expandedState = false }
                    ) {
                        limitDuration.forEachIndexed { index, label ->
                            DropdownMenuItem(onClick = {
                                selectedDuration = label
                                expenseInsightViewModel.getFilteredExpense(index)
                                expandedState = false
                            }) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.subtitle2,
                                    color = if (selectedDuration == label) {
                                        MaterialTheme.colors.primary
                                    } else {
                                        Color.Gray
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Total",
                    fontFamily = FontAverta,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                    letterSpacing = TextUnit(1.1f, TextUnitType.Sp),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = total.toString(),
                    style = MaterialTheme.typography.h3.copy(fontSize = 20.sp),
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontAverta,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                LazyColumn {
                    item {
                        if (filteredExpenses.isNotEmpty()) {
                            PieChart(
                                filteredCategories = filteredCategories,
                                percentProgress = percentProgress
                            )
                        }
                    }
//                    item {
//                        FlowRow(
//                            maxItemsInEachRow = 3,
//                            modifier = Modifier
//                                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
//                        ) {
//                            ExpenseItems(color = Color.Green)
//
//                        }
//                    }

                    if (filteredExpenses.isNotEmpty()) {
                        expenseListByDate.forEach { (date, expenses) ->
                            stickyHeader {
                                Spacer(modifier = Modifier.height(15.dp))
                                Text(
                                    text = date.toString(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(LightBlack)
                                        .padding(8.dp),
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontAverta
                                )
                            }
                            items(expenses) { expense ->
                                ExpenseItem(expense)
                            }
                        }
                    }
                }
                filteredExpenses.ifEmpty {
                    ListPlaceholder(
                        "No transaction. Tap the '+' button on the home menu to get started."
                    )
                }
            }
        }
    }
}