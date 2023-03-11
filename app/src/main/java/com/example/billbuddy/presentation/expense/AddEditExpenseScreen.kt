package com.example.billbuddy.presentation.expense

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billbuddy.ui.theme.*
import com.example.billbuddy.util.ExpenseCategoryIcon
import com.example.billbuddy.util.FontAverta
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditExpenseScreen(
    navController: NavController,
    addEditExpenseViewModel: AddEditExpenseViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val expenseAmount by addEditExpenseViewModel.expenseAmount.collectAsState()
    val context = LocalContext.current
    val currencyCode by addEditExpenseViewModel.paymentCurrency.collectAsState()


    LaunchedEffect(key1 = true) {
        addEditExpenseViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ExpenseUiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is ExpenseUiEvent.SaveExpense -> {
                    navController.navigateUp()
                    Toast.makeText(context, "Saved Expense Success", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 0.dp,
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 25.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Add an expense", style =
                            TextStyle(
                                fontFamily = FontAverta,
                                fontWeight = FontWeight.Bold,
                                fontSize = Heading
                            )
                        )
                    }
                },
                navigationIcon = {
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = PaddingValues(16.dp))
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Amount", style = TextStyle(
                            color = LightBlack200,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp
                        )
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = expenseAmount.expenseAmount.toString(),
                        onValueChange = { newValue ->
                            val amount = newValue.toDoubleOrNull() ?: 0.0
                            addEditExpenseViewModel.onEvent(
                                AddEditExpenseEvent.EnteredAmount(
                                    amount
                                )
                            )
                        },
                        textStyle = TextStyle(
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp
                        ),
                        label = {
                            Text(text = currencyCode)
                        },
                        singleLine = true,
                        modifier = Modifier
                            .width(160.dp)
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = LightGreen,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(50.dp))
                    Text(
                        text = "Choose the category", style = TextStyle(
                            color = LightBlack200,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp
                        )
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(15.dp))
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp),
                        mainAxisAlignment = MainAxisAlignment.SpaceBetween
                    ) {
                        ExpenseCategoryIcon.values().forEach { icon ->
                            ExpenseCategoryIconItem(expenseIcon = icon, onIconSelected = {
                                addEditExpenseViewModel.onEvent(
                                    AddEditExpenseEvent.ChooseExpenseCategory(
                                        it.icon.toString()
                                    )
                                )
                                addEditExpenseViewModel.onEvent(
                                    AddEditExpenseEvent.ExpenseCategoryTitle(it.title)
                                )
                                Log.d("titlecategory", "AddEditExpenseScreen: ${it.title}")
                                addEditExpenseViewModel.onEvent(
                                    AddEditExpenseEvent.ExpenseCategoryColor(it.bgRes.toString())
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
            .background(if (isSelected) DarkGreen else LightBlackUltra)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = expenseIcon.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp),
                colorFilter = if (isSelected) ColorFilter.tint(Color.White) else ColorFilter.tint(
                    Color.Black
                )
            )
            Text(
                text = expenseIcon.title,
                color = if (isSelected) Color.White else Color.Black,
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}


