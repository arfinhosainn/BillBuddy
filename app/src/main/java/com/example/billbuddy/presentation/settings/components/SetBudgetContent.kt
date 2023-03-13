package com.example.billbuddy.presentation.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.billbuddy.presentation.settings.SettingsViewModel
import com.example.billbuddy.services.BudgetLimitResetWorker
import com.example.billbuddy.ui.theme.spacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SetBudgetContent(
    bottomSheetState: BottomSheetScaffoldState,
    scope: CoroutineScope,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val MILLISECS = 86_400_000L
    val limitDuration = listOf(1 * MILLISECS, 7 * MILLISECS, 30 * MILLISECS)
    val limitDurationText by remember {
        mutableStateOf(
            listOf("Daily", "Weekly", "Monthly")
        )
    }

    var selectedIndex by remember { mutableStateOf(0) }
    val expenseLimitAmount by settingsViewModel.expenseLimit.collectAsState()
    val expenseLimitDuration by settingsViewModel.expenseLimitDuration.collectAsState()
    var selectedLimit by remember { mutableStateOf(limitDurationText[expenseLimitDuration]) }
    var isAmountEmpty by remember { mutableStateOf(false) }
    var limitTextFieldValue by remember { mutableStateOf(TextFieldValue(String())) }
    var expandedState by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf(Size.Zero) }

    val context = LocalContext.current

    var resetWorkRequest = PeriodicWorkRequestBuilder<BudgetLimitResetWorker>(
        limitDuration.first(),
        TimeUnit.MILLISECONDS,
        (limitDuration.first() * 0.95).toLong(),
        TimeUnit.MILLISECONDS
    ).build()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium)
    ) {
        Text(
            text = "SET LIMIT",
            style = MaterialTheme.typography.subtitle2
        )

        TextField(
            value = limitTextFieldValue,
            onValueChange = { field ->
                isAmountEmpty = false
                limitTextFieldValue = field
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = MaterialTheme.spacing.small,
                    bottom = MaterialTheme.spacing.medium
                ),
            maxLines = 1,
            singleLine = true,
            placeholder = {
                Text(
                    text = if (expenseLimitAmount == 0.0) "Amount" else expenseLimitAmount.toString(),
                    style = MaterialTheme.typography.subtitle2
                )
            },
            isError = isAmountEmpty,
            textStyle = MaterialTheme.typography.subtitle2,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = MaterialTheme.colors.primary,
                cursorColor = MaterialTheme.colors.primary,
                backgroundColor = Color.LightGray
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expandedState = !expandedState
                }
                .background(Color.LightGray)
                .padding(MaterialTheme.spacing.medium)
                .onGloballyPositioned {
                    size = it.size.toSize()
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedLimit,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(com.example.billbuddy.R.drawable.pop_up),
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface
            )

            DropdownMenu(
                expanded = expandedState,
                onDismissRequest = { expandedState = false },
                modifier = Modifier.width(
                    with(LocalDensity.current) {
                        size.width.toDp()
                    }
                )
            ) {
                limitDurationText.forEachIndexed { index, label ->
                    DropdownMenuItem(onClick = {
                        selectedLimit = label
                        expandedState = false
                        selectedIndex = index
                        resetWorkRequest = PeriodicWorkRequestBuilder<BudgetLimitResetWorker>(
                            limitDuration[index],
                            TimeUnit.MILLISECONDS,
                            (limitDuration[index] * 0.95).toLong(),
                            TimeUnit.MILLISECONDS
                        ).build()
                    }) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.subtitle2,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(
            onClick = {
                scope.launch { bottomSheetState.bottomSheetState.collapse()}
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.LightGray.copy(alpha = 0.4f),
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            Text(
                text = "CANCEL",
                style = MaterialTheme.typography.button
            )
        }

        TextButton(
            onClick = {
                scope.launch {
                    val amount = limitTextFieldValue.text
                    if (amount.isBlank())
                        isAmountEmpty = true
                    else {
                        isAmountEmpty = false
                        settingsViewModel.editExpenseLimit(limitTextFieldValue.text.toDouble())
                        bottomSheetState.bottomSheetState.collapse()
                        settingsViewModel.editLimitDuration(selectedIndex)
                        val workManager = WorkManager.getInstance(context)
                        workManager.enqueueUniquePeriodicWork(
                            "RESET",
                            ExistingPeriodicWorkPolicy.UPDATE,
                            resetWorkRequest
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.medium),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
            ),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            Text(
                text = "SET",
                style = MaterialTheme.typography.button
            )
        }
    }

}