package com.example.billbuddy.util

sealed class TransactionType(val title: String){
    object INCOME:TransactionType("income")
    object EXPENSE:TransactionType("expense")
}
