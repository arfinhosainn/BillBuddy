package com.example.billbuddy.presentation.expense

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
import com.example.billbuddy.R
import com.example.billbuddy.presentation.expense.components.ExpenseItem
import com.example.billbuddy.presentation.expense.components.PieChart
import com.example.billbuddy.presentation.navigation.Screens
import com.example.billbuddy.ui.theme.DarkGreen
import com.example.billbuddy.ui.theme.Heading
import com.example.billbuddy.ui.theme.LightBlack
import com.example.billbuddy.ui.theme.spacing
import com.example.billbuddy.util.ExpenseCategoryIcon
import com.example.billbuddy.util.FontAverta

@OptIn(ExperimentalUnitApi::class, ExperimentalFoundationApi::class)
@Composable
fun ExpenseInsightScreen(
    expenseInsightViewModel: ExpenseInsightViewModel = hiltViewModel(),
    navController: NavController
) {

    val scaffoldState = rememberScaffoldState()

    val expenseList by expenseInsightViewModel.fakeList.collectAsState()
    val expenseListByDate = expenseList.payments.groupBy { it.expenseDate.toLocalDate() }


    val filteredExpenses by expenseInsightViewModel.filteredExpense.collectAsState()

    val total = filteredExpenses.sumOf { it.expenseAmount }

    val groupData = filteredExpenses.groupBy { it.expenseCategory }

    val filteredCategories = mutableListOf<ExpenseCategoryIcon>()

    groupData.forEach { data ->
        val category = ExpenseCategoryIcon.values().find { it.icon.toString() == data.key }
        category?.let { filteredCategories.add(it) }
    }


    val amountList = groupData.map {
        it.value.sumOf { exp ->
            exp.expenseAmount
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
                "Last 3 Days", "This Week", "Last 14 Days", "This Month", "All"
            )
        )
    }
    var selectedDuration by remember {
        mutableStateOf(limitDuration.last())
    }
    Scaffold(
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
                            text = "Expenses", style =
                            TextStyle(
                                fontFamily = FontAverta,
                                fontWeight = FontWeight.Bold,
                                fontSize = Heading

                            )
                        )
                    },
                    actions = {
                        Text(
                            text = "Set Budget", style =
                            TextStyle(
                                fontFamily = FontAverta,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = DarkGreen
                            )
                        )

                    }
                )

            }
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screens.Expense.route) },
                backgroundColor = DarkGreen,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Icon"
                )

            }
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
                                    color = if (selectedDuration == label)
                                        MaterialTheme.colors.primary
                                    else
                                        Color.Gray
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

                    if (filteredExpenses.isNotEmpty()){
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


@Composable
fun ListPlaceholder(
    label: String =
        "No transaction has been made so far. Tap the '+' button to  get started"
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(
                start = MaterialTheme.spacing.medium,
                top = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium
            )
            .fillMaxSize()
    ) {
        Icon(
            painter = painterResource(R.drawable.home),
            tint = MaterialTheme.colors.onBackground,
            contentDescription = "no item added"
        )

        Text(
            text = label,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}