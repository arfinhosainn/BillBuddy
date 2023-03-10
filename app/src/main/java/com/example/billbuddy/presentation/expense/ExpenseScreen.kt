package com.example.billbuddy.presentation.expense

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billbuddy.presentation.your_payments.add_edit_payment.AddEditPaymentEvent
import com.example.billbuddy.ui.theme.DarkGreen
import com.example.billbuddy.ui.theme.Heading
import com.example.billbuddy.ui.theme.LightBlack200
import com.example.billbuddy.ui.theme.LightGreen
import com.example.billbuddy.util.ExpenseCategoryIcon
import com.example.billbuddy.util.FontAverta
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun AddEditExpenseScreen(
    navController: NavController,
    addEditExpenseViewModel: AddEditExpenseViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()

    val expenseAmount by addEditExpenseViewModel.expenseAmount.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 0.dp,
                title = {
                    Text(
                        modifier = Modifier.padding(start = 15.dp),
                        text = "Add an expense", style =
                        TextStyle(
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Bold,
                            fontSize = Heading
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back", tint = Color.Black
                        )

                    }
                },
                actions = {

                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "back", tint = Color.Black
                        )

                    }
                }
            )
        }
    ) { paddingValues ->

        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    Text(
                        text = "Amount", style = TextStyle(
                            color = LightBlack200,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(50.dp))
                    TextField(
                        value = expenseAmount.expenseAmount,
                        onValueChange = { amount ->
                            addEditExpenseViewModel.onEvent(AddEditExpenseEvent.EnteredAmount(amount))
                        },
                        modifier = Modifier
                            .width(150.dp)
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = LightGreen,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
                item {
                    Text(
                        text = "Choose the category", style = TextStyle(
                            color = LightBlack200,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                item {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 50.dp)
                    ) {
                        ExpenseCategoryIcon.values().forEach { icon ->
                            ExpenseCategoryIconItem(expenseIcon = icon, onIconSelected = {
                                addEditExpenseViewModel.onEvent(
                                    AddEditExpenseEvent.ChooseExpenseCategory(
                                        it.icon
                                    )
                                )
                            })
                        }
                    }
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomCenter), shape = RoundedCornerShape(10.dp),
                onClick = { addEditExpenseViewModel.onEvent(AddEditExpenseEvent.SaveExpense) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DarkGreen, contentColor = Color.White
                )
            ) {
                Text(
                    text = "Save",
                    style = TextStyle(
                        fontFamily = FontAverta,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

@Composable
fun ExpenseCategoryIconItem(
    expenseIcon: ExpenseCategoryIcon,
    onIconSelected: (ExpenseCategoryIcon) -> Unit,
) {
    var isSelected by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                isSelected = !isSelected
                onIconSelected(expenseIcon)
            }
            .background(if (isSelected) DarkGreen else LightGreen)
    ) {
        Image(
            painter = painterResource(id = expenseIcon.icon),
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .size(48.dp),
            colorFilter = if (isSelected) ColorFilter.tint(Color.White) else ColorFilter.tint(Color.Black)
        )
    }
}
