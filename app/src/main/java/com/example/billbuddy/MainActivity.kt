package com.example.billbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.billbuddy.data.local.model.Expense
import com.example.billbuddy.ui.theme.BillBuddyTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BillBuddyTheme {

                val expenseListByDate = mapOf(
                    "March 10, 2023" to listOf(
                        Expense(
                            "Clothes",
                            expenseCategoryColor = "",
                            expenseAmount = "33",
                            expenseCategory = "",
                            expenseDate = LocalDateTime.now()
                        ),
                        Expense(
                            "Clothes",
                            expenseCategoryColor = "",
                            expenseAmount = "33",
                            expenseCategory = "",
                            expenseDate = LocalDateTime.now()
                        ),
                        Expense(
                            "Clothes",
                            expenseCategoryColor = "",
                            expenseAmount = "33",
                            expenseCategory = "",
                            expenseDate = LocalDateTime.now()
                        )
                    ),
                    "March 9, 2023" to listOf(
                        Expense(
                            "Clothes",
                            expenseCategoryColor = "",
                            expenseAmount = "33",
                            expenseCategory = "",
                            expenseDate = LocalDateTime.now()
                        ),
                        Expense(
                            "Clothes",
                            expenseCategoryColor = "",
                            expenseAmount = "33",
                            expenseCategory = "",
                            expenseDate = LocalDateTime.now()
                        )
                    ),
                    "March 8, 2023" to listOf(
                        Expense(
                            "Clothes",
                            expenseCategoryColor = "",
                            expenseAmount = "33",
                            expenseCategory = "",
                            expenseDate = LocalDateTime.now()
                        ),
                        Expense(
                            "Clothes",
                            expenseCategoryColor = "",
                            expenseAmount = "33",
                            expenseCategory = "",
                            expenseDate = LocalDateTime.now()
                        )
                    )
                )

                ExpenseList(expenseListByDate)

//                val remNavController = rememberNavController()
//                NavigationGraph(navHostController = remNavController)

            }
        }
    }
}

