package com.example.billbuddy.presentation.auth

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billbuddy.R
import com.example.billbuddy.presentation.navigation.Screens
import com.example.billbuddy.ui.theme.DarkGreen
import com.example.billbuddy.ui.theme.LightBlack100
import com.example.billbuddy.util.FontAverta
import com.example.billbuddy.util.Resource
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    activity: Activity,
    viewModel: AuthScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    var mobile by remember {
        mutableStateOf("+880")
    }

    val scope = rememberCoroutineScope()

    var isDialog by remember {
        mutableStateOf(false)
    }

    if (isDialog) {
        CommonDialog()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 30.dp, vertical = 20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.logofull),
                contentDescription = "logo",
                modifier = Modifier.size(250.dp)
            )
            Text(
                modifier = Modifier.align(Start),
                text = "Welcome to BillBuddy",
                style =
                TextStyle(
                    fontFamily = FontAverta,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
            )
            Text(
                modifier = Modifier.align(Start),
                text = "Please enter your mobile number to get \nstarted on tracking your bills",
                style =
                TextStyle(
                    fontFamily = FontAverta,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    color = LightBlack100
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = mobile,
                onValueChange = {
                    mobile = it
                },
                label = { Text("Mobile Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                scope.launch {
                    viewModel.createUserWithPhone(
                        mobile,
                        activity
                    ).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                isDialog = false
                                activity.showMsg(result.data!!)
                                navController.navigate(Screens.OTPScreen.route)
                            }
                            is Resource.Error -> {
                                isDialog = false
                                activity.showMsg(result.message.toString())
                            }
                            is Resource.Loading -> {
                                isDialog = true
                            }
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = DarkGreen,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Send OTP",
                style = TextStyle(
                    fontFamily = FontAverta,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

//
// @Preview
// @Composable
// fun PreviewAuthScreen() {
//    AuthScreen()
// }

@Composable
fun CommonDialog() {
    Dialog(onDismissRequest = { }) {
        CircularProgressIndicator()
    }
}

fun Context.showMsg(
    msg: String,
    duration: Int = Toast.LENGTH_SHORT
) = Toast.makeText(this, msg, duration).show()