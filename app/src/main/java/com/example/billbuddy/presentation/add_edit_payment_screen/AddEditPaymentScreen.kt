package com.example.billbuddy.presentation.add_edit_payment_screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.billbuddy.ui.theme.*
import com.example.billbuddy.util.FontAverta
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AddEditPaymentScreen(
    navController: NavController,
    paymentViewModel: AddEditPaymentViewModel = hiltViewModel()
) {


    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }
    val icon = if (expanded) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropDown
    val focusManager = LocalFocusManager.current


    val titleState by paymentViewModel.paymentTitle.collectAsState()
    val amountState by paymentViewModel.paymentAmount.collectAsState()
    val payerNameState by paymentViewModel.payerName.collectAsState()
    val pickedDateState by paymentViewModel.paymentDate.collectAsState()
    val iconState by paymentViewModel.paymentIcon.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val calenderState = rememberSheetState()


    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern(
                "MMM dd yyyy"
            ).format(pickedDateState.paymentDate)
        }
    }

    CalendarDialog(state = calenderState,
        selection = CalendarSelection.Date { date ->
            paymentViewModel.onEvent(AddEditPaymentEvent.ChangePaymentDate(date))
        })


    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        paymentViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is UiEvent.SaveNote -> {
                    navController.navigateUp()
                    Toast.makeText(context, "Saved Note Success", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(backgroundColor = Color.White, elevation = 0.dp,
                title = {
                    Text(
                        text = "Back", style =
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
                }, actions = {

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Set a regular payment", style =
                    TextStyle(
                        fontFamily = FontAverta,
                        fontWeight = FontWeight.Bold,
                        fontSize = Heading
                    )
                )

                Text(
                    text = "Set Icon", style =
                    TextStyle(
                        fontFamily = FontAverta,
                        color = DarkGreen,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (titleState.text.isBlank()) "Type of payment*" else "Type of payment",
                style = TextStyle(
                    color = LightBlack200,
                    fontFamily = FontAverta,
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = titleState.text,
                onValueChange = {
                    paymentViewModel.onEvent(AddEditPaymentEvent.EnteredTitle(it))

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    unfocusedIndicatorColor = LightBlack100,
                    focusedIndicatorColor = LightGreen100
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = if (payerNameState.text.isBlank()) "Payee Name*" else "Payee Name",
                style = TextStyle(
                    color = LightBlack200,
                    fontFamily = FontAverta,
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = payerNameState.text,
                onValueChange = {
                    paymentViewModel.onEvent(AddEditPaymentEvent.EnteredPayerName(it))

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    unfocusedIndicatorColor = LightBlack100,
                    focusedIndicatorColor = LightGreen100
                )
            )
            Spacer(modifier = Modifier.height(20.dp))


            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(4f)
                ) {

                    Text(
                        modifier = Modifier.padding(bottom = 10.dp),
                        text = if (amountState.text.isBlank()) "Enter Amount*" else "Enter Amount",
                        style = TextStyle(
                            color = LightBlack200,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium
                        )
                    )

                }

                Column(
                    modifier = Modifier
                        .weight(3.5f)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 10.dp),
                        text = if (titleState.text.isBlank()) "Select Date*" else "Select Date",
                        style = TextStyle(
                            color = LightBlack200,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                OutlinedTextField(
                    value = amountState.text,
                    onValueChange = {
                        paymentViewModel.onEvent(
                            AddEditPaymentEvent.EnteredAmount(
                                it.toDoubleOrNull() ?: 0.0
                            )
                        )
                    },
                    modifier = Modifier
                        .weight(4f)
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        unfocusedIndicatorColor = LightBlack100,
                        focusedIndicatorColor = LightGreen100
                    )
                )

                Spacer(modifier = Modifier.width(15.dp))

                OutlinedTextField(
                    value = formattedDate,
                    onValueChange = { date ->
                        pickedDateState.paymentDate = LocalDate.parse(date)
                    },
                    readOnly = true,
                    modifier = Modifier
                        .weight(4f)
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        unfocusedIndicatorColor = LightBlack100,
                        focusedIndicatorColor = LightGreen100
                    ),
                    trailingIcon = {
                        IconButton(onClick = { calenderState.show() }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "back", tint = LightBlack200
                            )

                        }

                    }

                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(4f)
                ) {

                    Text(
                        modifier = Modifier.padding(bottom = 10.dp),
                        text = "Frequency",
                        style = TextStyle(
                            color = LightBlack200,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium
                        )
                    )

                }

                Column(
                    modifier = Modifier
                        .weight(3.5f)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 10.dp),
                        text = "Payment Method",
                        style = TextStyle(
                            color = LightBlack200,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium
                        )
                    )

                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                OutlinedTextField(
                    value = selectedOption,
                    readOnly = true,
                    onValueChange = {},
                    trailingIcon = {
                        Icon(icon, null)
                    },
                    modifier = Modifier
                        .weight(4f)
                        .onFocusChanged {
                            expanded = it.isFocused
                        }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                        focusManager.clearFocus()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    options.forEach { label ->
                        DropdownMenuItem(onClick = {
                            selectedOption = label
                            expanded = false
                            focusManager.clearFocus()
                        }) {
                            Text(text = label)
                        }
                    }
                }
                Spacer(modifier = Modifier.width(15.dp))

                OutlinedTextField(
                    value = selectedOption,
                    readOnly = true,
                    onValueChange = {},
                    trailingIcon = {
                        Icon(icon, null)
                    },
                    modifier = Modifier
                        .weight(4f)
                        .onFocusChanged {
                            expanded = it.isFocused
                        }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                        focusManager.clearFocus()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    options.forEach { label ->
                        DropdownMenuItem(onClick = {
                            selectedOption = label
                            expanded = false
                            focusManager.clearFocus()
                        }) {
                            Text(text = label)
                        }
                    }
                }


            }


            var switchState = remember {
                mutableStateOf(false)
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.Start) {

                    Text(
                        text = "Set Automatic payment",
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Automatically pays your bill on the date \nof payment. Turn it off anytime",
                        style = TextStyle(
                            color = LightBlack100,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 20.sp
                        )
                    )

                }
                Switch(checked = switchState.value, onCheckedChange = {
                    switchState.value = it
                })
            }
            Spacer(modifier = Modifier.height(80.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally), shape = RoundedCornerShape(10.dp),
                onClick = { paymentViewModel.onEvent(AddEditPaymentEvent.SavePayment) },
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


@Preview(showBackground = true)
@Composable
fun PreviewAddEditPaymentScreen() {
    val navController = rememberNavController()
    AddEditPaymentScreen(navController = navController)

}