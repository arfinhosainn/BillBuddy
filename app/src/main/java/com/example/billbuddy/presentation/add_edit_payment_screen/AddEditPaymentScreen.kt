package com.example.billbuddy.presentation.add_edit_payment_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.billbuddy.ui.theme.Heading
import com.example.billbuddy.ui.theme.LightBlack100
import com.example.billbuddy.ui.theme.LightBlack200
import com.example.billbuddy.ui.theme.LightGreen100
import com.example.billbuddy.util.FontAverta

@Composable
fun AddEditPaymentScreen(
    navController: NavController,
    paymentViewModel: AddEditPaymentViewModel = hiltViewModel()
) {

    val titleState by paymentViewModel.paymentTitle.collectAsState()
    val amountState by paymentViewModel.paymentAmount.collectAsState()
    val payerNameState by paymentViewModel.payerName.collectAsState()
    val paymentDate by paymentViewModel.paymentDate.collectAsState()

    val scaffoldState = rememberScaffoldState()


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(backgroundColor = Color.White,
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
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

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
            Spacer(modifier = Modifier.height(30.dp))
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
            Spacer(modifier = Modifier.height(30.dp))


            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier
                    .weight(4f)) {

                    Text(modifier  = Modifier.padding(bottom = 10.dp),
                        text = if (titleState.text.isBlank()) "Type of payment*" else "Type of payment",
                        style = TextStyle(
                            color = LightBlack200,
                            fontFamily = FontAverta,
                            fontWeight = FontWeight.Medium
                        )
                    )

                }

                Column(modifier = Modifier
                    .weight(3.5f)) {
                    Text(modifier  = Modifier.padding(bottom = 10.dp),
                        text = if (titleState.text.isBlank()) "Type of payment*" else "Type of payment",
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
                    value = titleState.text,
                    onValueChange = {

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
                    value = titleState.text,
                    onValueChange = {

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