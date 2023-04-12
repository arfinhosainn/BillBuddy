package com.example.billbuddy.presentation.reports

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun MonthRangeToggle(
    reportsViewModel: ReportsViewModel = hiltViewModel()
) {
    val startDate by reportsViewModel.startMonthAndYearState.collectAsState()

    val endDate by reportsViewModel.endMonthAndYearState.collectAsState()

    val startCalenderState = rememberSheetState()
    val endCalenderState = rememberSheetState()

    var startMonthYear by remember {
        mutableStateOf(
            YearMonth.from(startDate.startMonthAndYear).atDay(1).atStartOfDay()
        )
    }
    var endMonthYear by remember {
        mutableStateOf(
            YearMonth.from(endDate.endMonthAndYear).atEndOfMonth().atTime(23, 59, 59)
        )
    }

    startMonthYear = startMonthYear.withMonth(startDate.startMonthAndYear.monthValue)
        .withYear(startDate.startMonthAndYear.year)
        .withDayOfMonth(startMonthYear.dayOfMonth)

    endMonthYear = endMonthYear.withMonth(endDate.endMonthAndYear.monthValue)
        .withYear(endDate.endMonthAndYear.year)
        .withDayOfMonth(endMonthYear.dayOfMonth)

    CalendarDialog(
        state = startCalenderState,
        selection = CalendarSelection.Date { date ->
            val newDate = date.atStartOfDay()
            startMonthYear = newDate
            reportsViewModel.onEvent(ReportsEvent.StartMonthYear(newDate))
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            style = CalendarStyle.WEEK,
            disabledDates = emptyList()
        )
    )
    CalendarDialog(
        state = endCalenderState,
        selection = CalendarSelection.Date { date ->
            val newDate = date.atStartOfDay().plusDays(1)
            endMonthYear = newDate.minusSeconds(1)
            reportsViewModel.onEvent(ReportsEvent.EndMonthYear(newDate))
        }
    )

    LaunchedEffect(startMonthYear, endMonthYear) {
        reportsViewModel.onEvent(
            ReportsEvent.ChangeStartAndEndMonthYear(startMonthYear, endMonthYear)
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = startMonthYear.format(DateTimeFormatter.ofPattern("MM yyyy")),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clickable {
                    startCalenderState.show()
                }
        )

        Text(
            text = "-",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Text(
            text = endMonthYear.format(DateTimeFormatter.ofPattern("MM yyyy")),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clickable {
                    endCalenderState.show()
                }
        )
    }
}