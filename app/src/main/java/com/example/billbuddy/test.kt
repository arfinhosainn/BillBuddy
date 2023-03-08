package com.example.billbuddy

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

//@Composable
//fun TestScreen() {
//
//
//    var amount by remember { mutableStateOf(0.0) }
//
//    OutlinedTextField(
//        value = amount.toString(),
//        onValueChange = { value ->
//            amount = value.toDoubleOrNull() ?: 0.0
//        },
//        label = { Text("Amount") },
//        keyboardOptions = KeyboardOptions(
//            keyboardType = KeyboardType.Number
//        ),
//        keyboardActions = KeyboardActions(
//            onDone = { /* Handle done action */ }
//        ),
//        modifier = Modifier.fillMaxWidth()
//    )
//
//}
//
//
//@Preview
//@Composable
//fun PreviewTestScreen() {
//    TestScreen()
//
//}