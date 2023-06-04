package com.example.feature_auth.auth

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.billbuddy.util.Resource
import com.example.feature_auth.R
import com.example.ui.theme.DarkGreen
import com.example.ui.theme.FontAverta
import com.example.ui.theme.LightBlack100
import com.example.util.Screens
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